package com.emedinaa.kotlinapp

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.adapters.BaseAdapter
import com.emedinaa.kotlinapp.databinding.FragmentCategoriesBinding
import com.emedinaa.kotlinapp.model.Category
import com.emedinaa.kotlinapp.model.Data

/**
 * @author Eduardo Medina
 */
class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView (
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = object : BaseAdapter<Category>(Data.categories()) {
            override fun getViewHolder(parent: ViewGroup): BaseViewHolder<Category> {
                val rowView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.row_category, parent, false)
                return object : BaseAdapter.BaseViewHolder<Category>(rowView) {
                    override fun bind(entity: Category) {
                        rowView.findViewById<TextView>(R.id.textViewName).text = entity.name
                        rowView.findViewById<TextView>(R.id.textViewName).setOnClickListener {
                            onItemSelected(entity)
                        }
                    }
                }
            }
        }
    }

    private fun onItemSelected(category: Category) {
        findNavController().navigate(R.id.action_categoriesFragment_to_dishesFragment,
        bundleOf(Pair("CATEGORY",category)))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.categories_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logOutMenu -> {
                exit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun exit() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as AppCompatActivity).supportActionBar?.show()
        _binding = null
    }
}