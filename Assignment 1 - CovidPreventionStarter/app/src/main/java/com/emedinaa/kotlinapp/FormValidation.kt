package com.emedinaa.kotlinapp

sealed class FormValidation {
    object Ok:FormValidation()
    data class Error(val error:String):FormValidation()
}
