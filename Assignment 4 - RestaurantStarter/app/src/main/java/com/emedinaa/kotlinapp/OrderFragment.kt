package com.emedinaa.kotlinapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.emedinaa.kotlinapp.adapters.OrderAdapter
import com.emedinaa.kotlinapp.databinding.FragmentOrderBinding
import com.emedinaa.kotlinapp.model.Data

/**
 * @author Eduardo Medina
 */
class OrderFragment : Fragment() {
    private var _binding: FragmentOrderBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.adapter = OrderAdapter(Data.orders())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}