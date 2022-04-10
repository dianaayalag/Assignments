package com.emedinaa.kotlinapp

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.emedinaa.kotlinapp.databinding.FragmentDishBinding
import com.emedinaa.kotlinapp.model.Dish
import com.emedinaa.kotlinapp.utils.formatPrice

/**
 * @author Eduardo Medina
 */
class DishFragment : Fragment() {

    private var _binding: FragmentDishBinding? = null
    private val binding get() = _binding!!
    private var favorite:Boolean = false


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
            favorite = (it.favorite ==1) //1 favorite
            updateFavoriteIcon()
        }
    }


    private fun updateFavoriteIcon() {
        binding.imageViewFavorite.setColorFilter(if(favorite) Color.RED else Color.WHITE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}