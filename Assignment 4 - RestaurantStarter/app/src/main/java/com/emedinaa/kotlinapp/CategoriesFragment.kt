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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.adapters.BaseAdapter
import com.emedinaa.kotlinapp.databinding.FragmentCategoriesBinding
import com.emedinaa.kotlinapp.message.OperationResult
import com.emedinaa.kotlinapp.model.Category
import com.emedinaa.kotlinapp.model.Session
import com.emedinaa.kotlinapp.storage.AuthRepository
import com.emedinaa.kotlinapp.storage.CategoriesRepository
import com.emedinaa.kotlinapp.storage.remote.ApiClient
import com.emedinaa.kotlinapp.storage.remote.AuthRemoteDatasource
import com.emedinaa.kotlinapp.storage.remote.CategoriesRemoteDatasource
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.launch

/**
 * @author Eduardo Medina
 */
class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val categoriesRepository: CategoriesRepository by lazy {
        CategoriesRemoteDatasource(ApiClient())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = object : BaseAdapter<Category>(emptyList()) {
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

        fetchCategories()
    }

    private fun fetchCategories() {
        val token = (requireActivity()).intent.extras?.getParcelable<Session>("SESSION")?.token?:""

        lifecycleScope.launch {
            when(val result = categoriesRepository.getAll(token)) {
                is OperationResult.Success -> {
                    (binding.recyclerView.adapter as? BaseAdapter<Category>)?.update(result.data)
                }
                is OperationResult.Failure -> {
                    //error
                    Toast.makeText(requireContext(), result.exception.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun onItemSelected(category: Category) {
        findNavController().navigate(
            R.id.action_categoriesFragment_to_dishesFragment2,
            bundleOf(Pair("CATEGORY", category))
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.categories_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addMenu -> {
                addCategory()
                true
            }
            R.id.editMenu -> {
                editCategory()
                true
            }
            R.id.removeMenu -> {
                removeCategory()
                true
            }
            R.id.logOutMenu -> {
                exit()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addCategory() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_edit_category_dialog, null, false)
        val customTitleView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_title_dialog, null, false)
        customTitleView.findViewById<TextView>(R.id.tvTitle).text = "Agregar categoría"

        MaterialAlertDialogBuilder(requireContext())
            .setCustomTitle(customTitleView)
            .setView(dialogView)
            .setPositiveButton("Confirmar") { dialog, _ ->
                val name = dialogView.findViewById<TextInputLayout>(R.id.edtCategoryName).editText?.text.toString()
                val token = (requireActivity()).intent.extras?.getParcelable<Session>("SESSION")?.token?:""

                lifecycleScope.launch {
                    when(val result = categoriesRepository.addCategory(token, name)) {
                        is OperationResult.Success -> {
                            dialog.dismiss()
                            Toast.makeText(context, "Category $name was successfully created", Toast.LENGTH_SHORT).show()
                            refreshFragment()
                        }
                        is OperationResult.Failure -> {
                            //error
                            dialog.dismiss()
                            Toast.makeText(requireContext(), result.exception.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun editCategory() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_edit_category_dialog, null, false)
        val customTitleView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_title_dialog, null, false)
        customTitleView.findViewById<TextView>(R.id.tvTitle).text = "Editar categoría"
        dialogView.findViewById<TextInputLayout>(R.id.edtCategoryName).hint = "Coloque el nombre de la categoría que desea editar"

        MaterialAlertDialogBuilder(requireContext())
            .setCustomTitle(customTitleView)
            .setView(dialogView)
            .setPositiveButton("Confirmar") { dialog, _ ->
                val name = dialogView.findViewById<TextInputLayout>(R.id.edtCategoryName).editText?.text.toString()
                val token = (requireActivity()).intent.extras?.getParcelable<Session>("SESSION")?.token?:""

                lifecycleScope.launch {
                    when(val result = categoriesRepository.getAll(token)) {
                        is OperationResult.Success -> {
                            val matchingItems = result.data.filter { it.name.equals(name) }
                            if (matchingItems.isNotEmpty()) {
                                var category = matchingItems.first()
                                val dialogView2 = LayoutInflater.from(requireContext()).inflate(
                                    R.layout.fragment_add_edit_category_dialog,
                                    null,
                                    false
                                )
                                val customTitleView2 = LayoutInflater.from(requireContext())
                                    .inflate(R.layout.custom_title_dialog, null, false)
                                customTitleView2.findViewById<TextView>(R.id.tvTitle).text =
                                    "Editar categoría"
                                dialogView2.findViewById<TextInputLayout>(R.id.edtCategoryName).hint =
                                    "Coloque el nuevo nombre para la categoría"
                                MaterialAlertDialogBuilder(requireContext())
                                    .setCustomTitle(customTitleView2)
                                    .setView(dialogView2)
                                    .setPositiveButton("Confirmar") { dialog, _ ->
                                        val newName = dialogView2.findViewById<TextInputLayout>(R.id.edtCategoryName).editText?.text.toString()
                                        lifecycleScope.launch {
                                            when(val result = categoriesRepository.updateCategory(token, category.id, newName)) {
                                                is OperationResult.Success -> {
                                                    dialog.dismiss()
                                                    Toast.makeText(context, "Category $name was successfully updated", Toast.LENGTH_SHORT).show()
                                                    refreshFragment()
                                                }
                                                is OperationResult.Failure -> {
                                                    //error
                                                    dialog.dismiss()
                                                    Toast.makeText(requireContext(), result.exception.message, Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    }
                                    .setNegativeButton("Cancelar") { dialog, _ ->
                                        dialog.cancel()
                                    }
                                    .show()
                            }
                        }
                        is OperationResult.Failure -> {
                            //error
                            Toast.makeText(requireContext(), result.exception.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }
    private fun removeCategory() {
        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.fragment_add_edit_category_dialog, null, false)
        val customTitleView =
            LayoutInflater.from(requireContext()).inflate(R.layout.custom_title_dialog, null, false)
        customTitleView.findViewById<TextView>(R.id.tvTitle).text = "Eliminar categoría"

        MaterialAlertDialogBuilder(requireContext())
            .setCustomTitle(customTitleView)
            .setView(dialogView)
            .setPositiveButton("Confirmar") { dialog, _ ->
                val name =
                    dialogView.findViewById<TextInputLayout>(R.id.edtCategoryName).editText?.text.toString()
                val token =
                    (requireActivity()).intent.extras?.getParcelable<Session>("SESSION")?.token
                        ?: ""

                lifecycleScope.launch {
                    when (val result = categoriesRepository.getAll(token)) {
                        is OperationResult.Success -> {
                            val matchingItems = result.data.filter { it.name.equals(name) }
                            if (matchingItems.isNotEmpty()) {
                                var category = matchingItems.first()
                                lifecycleScope.launch {
                                    when(val result = categoriesRepository.deleteCategory(token, category.id)) {
                                        is OperationResult.Success -> {
                                            dialog.dismiss()
                                            Toast.makeText(context, "Category $name was successfully deleted", Toast.LENGTH_SHORT).show()
                                            refreshFragment()
                                        }
                                        is OperationResult.Failure -> {
                                            //error
                                            dialog.dismiss()
                                            Toast.makeText(requireContext(), result.exception.message, Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun refreshFragment() {
        activity?.recreate()
    }

    private fun exit() {
        //findNavController().popBackStack()
        requireActivity().finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}