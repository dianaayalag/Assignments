package com.emedinaa.kotlinapp.storage

import com.emedinaa.kotlinapp.model.Dish

interface DishRepository {

    fun getAll(callback:(dishes:List<Dish>)-> Unit)
    fun getAll(category:Int,callback:(dishes:List<Dish>)-> Unit)
    fun getAllFavorites(callback:(dishes:List<Dish>)-> Unit)
    fun getAllFavorites(category:Int,callback:(dishes:List<Dish>)-> Unit)

    suspend fun getAll():List<Dish>
    suspend fun getAll(category:Int):List<Dish>
    suspend fun getAllFavorites():List<Dish>
    suspend fun getAllFavorites(category:Int):List<Dish>

    fun add(dish: Dish, callback:()-> Unit)
    suspend fun favorite(dishId:Int, isFavorite:Boolean)
}