package com.emedinaa.kotlinapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    var name: String?,
    var desc: String?
) : Parcelable

@Parcelize
data class Dish(
    val id: Int,
    val category: Int,
    val name: String,
    val photo: String,
    val price: String,
    val desc: String,
    val favorite:Int = 0
) : Parcelable

@Parcelize
data class Order(
    val id: Int,
    val idDish: Int, val name: String, val price: Double, val amount: Double
) : Parcelable

open class OrderViewType {
    open fun isHeader(): Boolean = false
    open fun isItem(): Boolean = false
    open fun isFooter(): Boolean = false
}

class OrderViewHeader : OrderViewType() {
    override fun isHeader(): Boolean = true
}

class OrderViewFooter(val total: Double) : OrderViewType() {
    override fun isFooter(): Boolean = true
}

data class CartItem(
    var order: String?,
    var amount: Int,
    val dishId: String,
    val name: String,
    val price: Double
) : OrderViewType() {

    override fun isItem(): Boolean = true

    fun total(): Double = amount * price

    override fun toString(): String {
        return "CartItem(order=$order, amount=$amount, dishId='$dishId', name='$name', price=$price, total=${total()})"
    }
}