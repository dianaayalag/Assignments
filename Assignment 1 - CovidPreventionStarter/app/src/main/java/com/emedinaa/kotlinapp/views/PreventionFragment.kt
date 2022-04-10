package com.emedinaa.kotlinapp.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emedinaa.kotlinapp.Prevention
import com.emedinaa.kotlinapp.databinding.FragmentPreventionBinding

/**
 * @author Eduardo Medina
 */
private  const val TAG = "PREVENTION_FRAG"
class PreventionFragment : Fragment() {

    private var _binding: FragmentPreventionBinding? = null
    private val binding get() = _binding!!

    private var prevention:Prevention? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prevention = (arguments?.getParcelable("ENTITY") as? Prevention)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPreventionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //TODO populate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        populate()
    }

    private fun  populate() {
        //if(prevention!= null)
        /*prevention?.let {
            binding.textViewTitle.text = it.title
            binding.textViewDesc.text = it.desc
            binding.imageView.setImageResource(it.image)
        }*/

        (arguments?.getParcelable("ENTITY") as? Prevention)?.let {
            binding.textViewTitle.text = it.title
            binding.textViewDesc.text = it.desc
            binding.imageView.setImageResource(it.image)
        }
    }

}