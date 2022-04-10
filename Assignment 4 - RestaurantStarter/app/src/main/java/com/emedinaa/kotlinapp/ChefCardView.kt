/**
 * Copyright 2020 Academia Móviles
 * https://www.academiamoviles.com/
 * @author : Eduardo Medina
 */
package com.emedinaa.kotlinapp

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 * @author Eduardo Medina
 */
class ChefCardView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        addView(View.inflate(context, R.layout.layout_chef_view, null))
    }
}