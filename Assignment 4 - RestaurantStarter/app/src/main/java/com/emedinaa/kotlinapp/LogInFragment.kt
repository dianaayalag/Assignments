package com.emedinaa.kotlinapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.databinding.FragmentLogInBinding
import com.emedinaa.kotlinapp.message.OperationResult
import com.emedinaa.kotlinapp.model.Session
import com.emedinaa.kotlinapp.storage.AuthRepository
import com.emedinaa.kotlinapp.storage.remote.ApiClient
import com.emedinaa.kotlinapp.storage.remote.AuthRemoteDatasource
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

/**
 * @author Eduardo Medina
 */
class LogInFragment : Fragment() {

    private val authRepository: AuthRepository by lazy {
        AuthRemoteDatasource(ApiClient())
    }
    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //mock data
        binding.textInputLayoutEmail.editText?.setText("cliente@gmail.com")
        binding.textInputLayoutPassword.editText?.setText("cliente")

        binding.buttonLogin.setOnClickListener {
            //goToMainView()
            logIn()
        }
    }

    private fun logIn() {
        //validate form
        val username = binding.textInputLayoutEmail.editText?.text.toString().trim()
        val password = binding.textInputLayoutPassword.editText?.text.toString().trim()

        lifecycleScope.launch {
            //val result = authRepository.login(username,password)

            //main thread
            when (val result = authRepository.login(username, password)) {
                is OperationResult.Success -> {
                    goToMainView(result.data)
                }
                is OperationResult.Failure -> {
                    showError(result.exception)
                }
            }
        }

    }

    private fun showError(exception: Exception) {
        Snackbar.make(binding.root, "Error $exception", Snackbar.LENGTH_SHORT)
            .show()
    }

    private fun goToMainView(session: Session) {
        findNavController().navigate(
            R.id.action_logInFragment_to_mainActivity,
            bundleOf(Pair("SESSION", session))
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}