package com.emedinaa.kotlinapp.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.emedinaa.kotlinapp.FormValidation
import com.emedinaa.kotlinapp.R
import com.emedinaa.kotlinapp.Utils
import com.emedinaa.kotlinapp.databinding.FragmentLogInBinding
import com.emedinaa.kotlinapp.isInvalidEmail
import com.emedinaa.kotlinapp.toast

/**
 * @author Eduardo Medina
 */
class LogInFragment : Fragment() {

    private var _binding: FragmentLogInBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //mock
        //binding.textInputLayoutUserName.editText?.setText("admin@admin.com")
        //binding.textInputLayoutPassword.editText?.setText("123456")

        //TODO events
        binding.buttonLogIn.setOnClickListener {
            Log.v("CONSOLE", "login click")
            /*if(validateForm()) {
                toast("show Dialog")
                showMessage()
            }*/
            when(val result = validaFormWithSC()) {
                is FormValidation.Ok -> {
                    toast("show Dialog")
                    showMessage()
                }
                is FormValidation.Error -> {
                    toast(result.error)
                }
            }
        }

        binding.buttonSignUp.setOnClickListener {
            gotoSignUpView()
        }
    }

    //TODO validate form
    private fun validaFormWithSC():FormValidation {
        clearForm()
        val username = binding.textInputLayoutUserName.editText?.text.toString().trim()
        val password = binding.textInputLayoutPassword.editText?.text.toString().trim()
        Log.v("CONSOLE", "username $username password $password")

        if(username.isEmpty()) {
            return FormValidation.Error("Ingresar username")
        }

        if(username.isInvalidEmail()) {
            return FormValidation.Error("Username inválido")
        }

        if(password.isEmpty()) {
            return FormValidation.Error("Ingresar password")
        }
        return FormValidation.Ok
    }

    private fun validateForm():Boolean {
        clearForm()
        val username = binding.textInputLayoutUserName.editText?.text.toString().trim()
        val password = binding.textInputLayoutPassword.editText?.text.toString().trim()
        Log.v("CONSOLE", "username $username password $password")

        if(username.isEmpty()) {
            binding.textInputLayoutUserName.error = "Ingresar username"
            return false
        }

        /*if(Utils.isInvalidEmail(username)) {
            binding.textInputLayoutUsername.error = "Username inválido"
            return false
        }*/
        if(username.isInvalidEmail()) {
            binding.textInputLayoutUserName.error = "Username inválido"
            return false
        }

        if(password.isEmpty()) {
            binding.textInputLayoutPassword.error = "Ingresar password"
            return false
        }
        return true
    }

    //TODO clear
    private fun clearForm() {
        binding.textInputLayoutUserName.error = null
        binding.textInputLayoutPassword.error = null
    }

    //TODO dialog
    private fun showMessage() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("")
        builder.setMessage("Enviando al servidor...")
        builder.setPositiveButton("Aceptar") { _, _ ->
            gotoHomeView()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    //TODO navigate to home view

    private fun gotoHomeView() {
        findNavController().navigate(R.id.action_logInFragment_to_homeFragment)
    }

    private fun gotoSignUpView() {
        findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
    }

}