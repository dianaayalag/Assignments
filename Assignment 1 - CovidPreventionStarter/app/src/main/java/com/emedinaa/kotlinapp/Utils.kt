package com.emedinaa.kotlinapp

import android.util.Patterns

//no usar
object Utils {
    fun isInvalidEmail(text:String) = !Patterns.EMAIL_ADDRESS.matcher(text).matches()
}