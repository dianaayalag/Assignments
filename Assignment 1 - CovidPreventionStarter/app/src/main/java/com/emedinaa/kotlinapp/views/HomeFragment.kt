package com.emedinaa.kotlinapp.views

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.Data
import com.emedinaa.kotlinapp.Prevention
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.databinding.FragmentHomeBinding
import com.emedinaa.kotlinapp.toast

/**
 * @author Eduardo Medina
 */
private const val TAG = "HOME_FRAG"

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //TODO adapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //adapter
        binding.recyclerView.adapter = PreventionAdapter(emptyList()) {
            goToDetailView(it)
        }

        //retrieveData()
        retrieveDbData()
    }

    private fun itemAction():(item:Prevention)-> Unit {
        return {

        }
    }

    //TODO setear data
    private fun setData() {
        val data = Data.getPreventions()
        //val adapter = PreventionAdapter(data)
        //binding.recyclerView.adapter = adapter
        (binding.recyclerView.adapter as PreventionAdapter).update(data)
    }

    //TODO setear data

    private fun transformData() {
        val data = Data.getPreventionsDB().map {
            it.toPrevention()
            //Prevention(it.id, it.title, it.description)
        }.filter {
            it.id % 2 == 0
        }

        (binding.recyclerView.adapter as PreventionAdapter).update(data)
    }

    //TODO cargar data
    private fun retrieveData() {
        Handler(Looper.getMainLooper()).postDelayed({
            setData()
        },2000)
    }
    //TODO cargar db data
    private fun retrieveDbData() {
        Handler(Looper.getMainLooper()).postDelayed({
            transformData()
        },2000)
    }
    //TODO navegar a la siguiente pantalla

    private fun goToDetailView(item: Prevention) {
        //TODO toast
        Log.v(TAG, item.toString())
        toast(item.toString())
        //val bundle = Bundle()
        //bundle.putParcelable("ENTITY",item)

        //findNavController().navigate(R.id.action_homeFragment_to_preventionFragment,bundle)
        /*findNavController().navigate(R.id.action_homeFragment_to_preventionFragment,Bundle().apply {
            putParcelable("ENTITY",item)
        })*/
        val bundle = bundleOf(Pair("ENTITY",item))
        findNavController().navigate(R.id.action_homeFragment_to_preventionFragment, bundle)
    }

}