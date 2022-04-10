package com.emedinaa.kotlinapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.emedinaa.kotlinapp.databinding.FragmentDishBinding
import com.emedinaa.kotlinapp.model.CartItem
import com.emedinaa.kotlinapp.model.Data
import com.emedinaa.kotlinapp.model.Dish
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * @author Eduardo Medina
 */
class DishFragment : Fragment() {

    private var _binding: FragmentDishBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (arguments?.getParcelable("DISH") as? Dish)?.let {
            val price = it.price.formatPrice("S/.", 0.5f)
            binding.textViewTitle.text = it.name
            binding.textViewDesc.text = it.desc
            binding.textViewPrice.text = price
        }

        binding.buttonOrder.setOnClickListener {
            showAddDishDialog()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showAddDishDialog() {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_dish_dialog, null, false)
        val customTitleView = LayoutInflater.from(requireContext()).inflate(R.layout.custom_title_dialog, null, false)
        val quantityTextView = dialogView.findViewById<TextView>(R.id.tvQuantity)
        var quantity = (quantityTextView.text ?: "0").toString().toInt()
        dialogView.findViewById<AppCompatButton>(R.id.btnMinus).setOnClickListener {
            if (quantity <= 2) {
                it.isClickable = false
            }
            quantity--
            quantityTextView.text = quantity.toString()
        }
        dialogView.findViewById<AppCompatButton>(R.id.btnPlus).setOnClickListener {
            if (quantity == 1) {
                dialogView.findViewById<AppCompatButton>(R.id.btnMinus).isClickable = true
            }
            quantity++
            quantityTextView.text = quantity.toString()
        }
        dialogView.findViewById<AppCompatButton>(R.id.btnMinus).isClickable = false
        MaterialAlertDialogBuilder(requireContext())
            .setCustomTitle(customTitleView)
            .setView(dialogView)
            .setPositiveButton("Confirmar") { dialog, _ ->
                (arguments?.getParcelable("DISH") as? Dish)?.let {
                    val item = CartItem(order = "100", amount = quantity, name = it.name, dishId = it.id.toString(), price = it.price.toDouble())
                    Data.orders.add(item)

                }
            }.show()
    }

}