package com.emedinaa.kotlinapp.storage

import com.emedinaa.kotlinapp.model.Category

interface CategoryRepository {

    fun getAll(callback:(categories:List<Category>)-> Unit)
    fun add(category: Category,callback:()-> Unit)
    fun delete(id: Int, callback: () -> Unit)
    fun edit(category: Category, callback: () -> Unit)
}