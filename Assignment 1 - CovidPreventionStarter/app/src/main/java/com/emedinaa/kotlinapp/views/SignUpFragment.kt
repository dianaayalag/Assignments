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
import com.emedinaa.kotlinapp.databinding.FragmentSignUpBinding
import com.emedinaa.kotlinapp.isInvalidEmail
import com.emedinaa.kotlinapp.toast

/**
 * @author Eduardo Medina
 */
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSignUp.setOnClickListener {
            Log.v("CONSOLE", "signup click")
            if (validaForm()) {
                    showMessage()
            }
        }
    }

    private fun validaForm():Boolean {
        clearForm()
        val userName = binding.textInputLayoutUserName.editText?.text.toString().trim()
        val userLastName = binding.textInputLayoutUserLastName.editText?.text.toString().trim()
        val userEmail = binding.textInputLayoutUserEmail.editText?.text.toString().trim()
        val firstPassword = binding.textInputLayoutUserFirstPassword.editText?.text.toString().trim()
        val secondPassword = binding.textInputLayoutUserSecondPassword.editText?.text.toString().trim()

        Log.v("CONSOLE", "userName $userName, userLastName $userLastName, userEmail $userEmail, firstPassword $firstPassword, secondPassword $secondPassword")

        // To check if names and last names are invalid
        val reg = "^[a-zA-Z ]+$".toRegex()

        if(userName.isEmpty()) {
            binding.textInputLayoutUserName.error = "Name is required"
            return false
        }

        if(!reg.matches(userName)) {
            binding.textInputLayoutUserName.error = "Please, provide a valid name"
            return false
        }

        if(userLastName.isEmpty()) {
            binding.textInputLayoutUserLastName.error = "Last Name is required"
            return false
        }

        if(!reg.matches(userLastName)) {
            binding.textInputLayoutUserLastName.error = "Please, provide a valid last name"
            return false
        }

        if(userEmail.isEmpty()) {
            binding.textInputLayoutUserEmail.error = "E-mail is required"
            return false
        }

        if(userEmail.isInvalidEmail()) {
            binding.textInputLayoutUserEmail.error ="Please provide a valid e-mail"
            return false
        }

        if(firstPassword.isEmpty()) {
            binding.textInputLayoutUserFirstPassword.error = "Password is required"
            return false
        }

        if(firstPassword.count() < 8) {
            binding.textInputLayoutUserFirstPassword.error = "Password must be at least 8 characters"
            return false
        }

        if(secondPassword.isEmpty()) {
            binding.textInputLayoutUserSecondPassword.error = "Password is required"
            return false
        }

        if(firstPassword!=secondPassword) {
            binding.textInputLayoutUserSecondPassword.error ="Both passwords must match"
            return false
        }
        return true
    }

    private fun clearForm() {
        binding.textInputLayoutUserName.error = null
        binding.textInputLayoutUserLastName.error = null
        binding.textInputLayoutUserEmail.error = null
        binding.textInputLayoutUserFirstPassword.error = null
        binding.textInputLayoutUserSecondPassword.error = null
    }

    private fun showMessage() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle("")
        builder.setMessage("Your account was created succesfully")
        builder.setPositiveButton("Aceptar") { _, _ ->
            gotoHomeView()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun gotoHomeView() {
        findNavController().navigate(R.id.action_signUpFragment_to_homeFragment)
    }
}