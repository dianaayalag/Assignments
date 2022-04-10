package com.emedinaa.kotlinapp.di

import android.content.Context
import com.emedinaa.kotlinapp.storage.CategoryRepository
import com.emedinaa.kotlinapp.storage.db.CategoryDataSource
import com.emedinaa.kotlinapp.storage.db.RestaurantDataBase
import com.emedinaa.kotlinapp.utils.AppExecutors

object ServiceLocator {

    private val appExecutors = AppExecutors()
    private lateinit var restaurantDataBase: RestaurantDataBase
    private var categoryRepository:CategoryRepository? = null

    private fun createCategoryRepository():CategoryRepository {
        val repository = CategoryDataSource(restaurantDataBase, appExecutors)
        categoryRepository = repository
        return repository
    }

    fun setup(context: Context) {
        restaurantDataBase = RestaurantDataBase.getInstance(context)
    }

    fun provideCategoryRepository() = categoryRepository ?: createCategoryRepository()

    fun destroy() {
        RestaurantDataBase.destroyInstance()
        categoryRepository = null
    }

}