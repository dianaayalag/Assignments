package com.emedinaa.kotlinapp.storage

import com.emedinaa.kotlinapp.message.OperationResult
import com.emedinaa.kotlinapp.model.Category

interface CategoriesRepository {

    suspend fun getAll(token:String):OperationResult<List<Category>>
    suspend fun addCategory(token:String, name: String):OperationResult<Category>
    suspend fun updateCategory(token:String, id: String, name: String):OperationResult<String>
    suspend fun deleteCategory(token:String, id: String):OperationResult<String>

}