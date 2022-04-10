package com.emedinaa.kotlinapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.adapters.BaseAdapter
import com.emedinaa.kotlinapp.databinding.FragmentDishesBinding
import com.emedinaa.kotlinapp.model.Category
import com.emedinaa.kotlinapp.model.Data
import com.emedinaa.kotlinapp.model.Dish

/**
 * @author Eduardo Medina
 */
class DishesFragment : Fragment() {

    private var _binding: FragmentDishesBinding? = null
    private val binding get() = _binding!!

    private var category: Category? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        category = arguments?.getParcelable("CATEGORY") as? Category
        setHasOptionsMenu(true)
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

        val dishes: List<Dish> =
            if (category != null) Data.dishesByCategory(category?.id ?: -1) else Data.dishes()

        binding.recyclerView.adapter = object : BaseAdapter<Dish>(dishes) {
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
    }

    private fun onItemSelected(dish: Dish) {

        val action = if(category!=null)R.id.action_dishesFragment_to_dishFragment else R.id.action_dishesFragment2_to_dishFragment3
        findNavController().navigate(action, bundleOf(Pair("DISH",dish)))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.dishes_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.filterMenu -> {
                showFilter()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showFilter() {
        Toast.makeText(requireContext(), "Show Filter dialog", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}