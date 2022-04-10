package com.emedinaa.kotlinapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.adapters.BaseAdapter
import com.emedinaa.kotlinapp.databinding.FragmentDishesBinding
import com.emedinaa.kotlinapp.model.Category
import com.emedinaa.kotlinapp.model.Dish
import com.emedinaa.kotlinapp.storage.DishRepository
import com.emedinaa.kotlinapp.storage.db.DishDataSource
import com.emedinaa.kotlinapp.storage.db.RestaurantDataBase
import com.emedinaa.kotlinapp.utils.AppExecutors
import com.emedinaa.kotlinapp.utils.formatPrice
import com.emedinaa.kotlinapp.utils.showCurrentThreadInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * @author Eduardo Medina
 */
class DishesFragment : Fragment() {

    private val dishRepository: DishRepository by lazy {
        DishDataSource(
            RestaurantDataBase.getInstance(requireContext().applicationContext), AppExecutors()
        )
    }

    private var _binding: FragmentDishesBinding? = null
    private val binding get() = _binding!!

    private var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getParcelable("CATEGORY") as? Category
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val dishes: List<Dish> = if (category != null) Data.dishesByCategory(category?.id ?: -1) else Data.dishes()

        binding.recyclerView.adapter = object : BaseAdapter<Dish>(emptyList()) {
            override fun getViewHolder(parent: ViewGroup): BaseViewHolder<Dish> {
                val rowView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_dish, parent, false)
                return object : BaseAdapter.BaseViewHolder<Dish>(rowView) {
                    override fun bind(entity: Dish) {
                        val price = entity.price.formatPrice("S/.", 0.5f)
                        rowView.findViewById<TextView>(R.id.textViewTitle).text = entity.name
                        rowView.findViewById<TextView>(R.id.textViewDesc).text = entity.desc
                        rowView.findViewById<TextView>(R.id.textViewPrice).text = price

                        rowView.setOnClickListener {
                            onItemSelected(entity)
                        }
                    }
                }
            }
        }

        //events
        binding.textViewMenu.setOnClickListener {
            fetchDishesWithCoroutines()
        }

        binding.textViewFavorites.setOnClickListener {
            fetchDishesWithCoroutines(true)
        }
        //fetchDishes()
        fetchDishesWithCoroutines()
    }

    private fun updateList(dishes: List<Dish>) {
        //update adapter
        (binding.recyclerView.adapter as? BaseAdapter<Dish>)?.update(dishes)
    }

    private fun fetchDishesWithCoroutines(favorite:Boolean= false) {
        Log.v("CONSOLE", showCurrentThreadInfo())
        lifecycleScope.launch {

            /*val dishes = withContext(Dispatchers.IO) {
                Log.v("CONSOLE", showCurrentThreadInfo())
                if (category != null) {
                    dishRepository.getAll(category?.id ?: -1)
                } else {
                    dishRepository.getAll()
                }
            }*/
            val dishes = if (category != null) {
                val categoryId = category?.id ?: -1
                if(favorite) dishRepository.getAllFavorites(categoryId) else dishRepository.getAll(categoryId)
            } else {
                if(favorite) dishRepository.getAllFavorites() else dishRepository.getAll()
            }
            //main thread
            Log.v("CONSOLE", showCurrentThreadInfo())
            updateList(dishes)
        }
    }

    private fun fetchDishes() {
        if (category != null) {
            dishRepository.getAll(category?.id ?: -1) {
                updateList(it)
            }
        } else {
            dishRepository.getAll {
                updateList(it)
            }
        }
    }

    private fun onItemSelected(dish: Dish) {
        val action =
            if (category != null) R.id.action_dishesFragment2_to_dishFragment else R.id.action_dishesFragment_to_dishFragment2
        findNavController().navigate(action, bundleOf(Pair("DISH", dish)))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}