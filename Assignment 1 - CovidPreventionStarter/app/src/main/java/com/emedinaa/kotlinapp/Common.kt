package com.emedinaa.kotlinapp

import android.util.Patterns
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * @author Eduardo Medina
 */

fun String.isInvalidEmail():Boolean = !Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun Fragment.toast(text:String) = Toast.makeText(this.requireContext(),text,Toast.LENGTH_SHORT).show()