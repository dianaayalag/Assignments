/**
 * Copyright 2020 Academia Móviles
 * https://www.academiamoviles.com/
 * @author : Eduardo Medina
 */
package com.emedinaa.kotlinapp

import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.SuperscriptSpan


/**
 * @author Eduardo Medina
 */
fun String.formatPrice(currency: String, proportion: Float = 0.5f): SpannableString {
    val spannableString = SpannableString(currency.plus(this))
    spannableString.setSpan(SuperscriptSpan(), 0, currency.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannableString.setSpan(RelativeSizeSpan(proportion), 0, currency.length, 0)
    return spannableString
}