package com.emedinaa.kotlinapp.model

object Data {


    fun orders() = listOf(OrderViewHeader(),CartItem("100",2,"1","Arroz con Pollo", 15.90),
        CartItem("100",1,"1","Tallarines Rojos", 15.90),   CartItem("100",3,"1","Caldo de Gallina", 15.90),
        OrderViewFooter(31.80))
}