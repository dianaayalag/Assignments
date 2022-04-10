package com.emedinaa.kotlinapp.storage

import com.emedinaa.kotlinapp.message.OperationResult
import com.emedinaa.kotlinapp.model.Dish

interface DishesRepository {

    suspend fun getAll(token:String):OperationResult<List<Dish>>
    suspend fun getAll(token:String,category:String):OperationResult<List<Dish>>
}