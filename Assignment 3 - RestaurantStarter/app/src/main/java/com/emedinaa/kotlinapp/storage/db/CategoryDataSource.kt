package com.emedinaa.kotlinapp.storage.db

import com.emedinaa.kotlinapp.model.Category
import com.emedinaa.kotlinapp.storage.CategoryRepository
import com.emedinaa.kotlinapp.utils.AppExecutors

class CategoryDataSource(
    private val restaurantDataBase: RestaurantDataBase,
    private val appExecutors: AppExecutors
):CategoryRepository {

    private val categoryDao = restaurantDataBase.categoryDao()
    override fun getAll(callback: (categories: List<Category>) -> Unit) {
        appExecutors.diskIO.execute {
            val categories = categoryDao.getAll().map {
                it.toCategory()
            }
            appExecutors.mainThread.execute {
                callback(categories)
            }
        }
    }

    override fun add(category: Category, callback: () -> Unit) {
        appExecutors.diskIO.execute {
            val categoryDTO = CategoryDTO(
                category.name, category.desc
            )
            categoryDao.insert(categoryDTO)
            appExecutors.mainThread.execute {
                callback()
            }
        }
    }

    override fun delete(id: Int, callback: () -> Unit) {
        appExecutors.diskIO.execute {
            categoryDao.delete(id)
            appExecutors.mainThread.execute {
                callback()
            }
        }
    }

    override fun edit(category: Category, callback: () -> Unit) {
        appExecutors.diskIO.execute {
            val categoryDTO = CategoryDTO(
                category.id, category.name, category.desc
            )
            categoryDao.update(categoryDTO)
            appExecutors.mainThread.execute {
                callback()
            }
        }
    }
}