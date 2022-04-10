package com.emedinaa.kotlinapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author Eduardo Medina
 */

@Parcelize
data class Prevention(val id: Int, val title: String, val desc: String, val image: Int) :
    Parcelable {
    override fun toString(): String {
        return "Prevention(id=$id, title='$title', desc='$desc', image=$image)"
    }
}

data class PreventionDb(val id: Long, val title: String, val description: String, val photo: Int) {

    fun toPrevention()= Prevention(id.toInt(), title, description, photo)
}