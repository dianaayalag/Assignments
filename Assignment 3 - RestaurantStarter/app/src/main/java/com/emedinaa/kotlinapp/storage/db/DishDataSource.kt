package com.emedinaa.kotlinapp.storage.db

import android.util.Log
import com.emedinaa.kotlinapp.model.Dish
import com.emedinaa.kotlinapp.storage.DishRepository
import com.emedinaa.kotlinapp.utils.AppExecutors
import com.emedinaa.kotlinapp.utils.showCurrentThreadInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DishDataSource(
    private val restaurantDataBase: RestaurantDataBase,
    private val appExecutors: AppExecutors
) : DishRepository {

    private val dishDao = restaurantDataBase.dishDao()


    override fun getAll(callback: (dishes: List<Dish>) -> Unit) {
        appExecutors.diskIO.execute {
            val dishes = dishDao.getAll().map {
                it.toDish()
            }
            appExecutors.mainThread.execute {
                callback(dishes)
            }
        }
    }

    override fun getAll(category: Int, callback: (dishes: List<Dish>) -> Unit) {
        appExecutors.diskIO.execute {
            val dishes = dishDao.getAllByCategory(category).map {
                it.toDish()
            }
            appExecutors.mainThread.execute {
                callback(dishes)
            }
        }
    }

    override fun getAllFavorites(callback: (dishes: List<Dish>) -> Unit) {

    }

    override fun getAllFavorites(category: Int, callback: (dishes: List<Dish>) -> Unit) {

    }

    override fun add(dish: Dish, callback: () -> Unit) {
        appExecutors.diskIO.execute {
            val dishDTO = DishDTO(
                dish.category, dish.name, dish.photo, dish.price, dish.desc
            )
            dishDao.insert(dishDTO)
            appExecutors.mainThread.execute {
                callback()
            }
        }
    }


    /* override suspend fun getAll(): List<Dish> {
       return dishDao.getAllWithCoroutines().map {
           it.toDish()
       }
     }*/

    override suspend fun getAll(): List<Dish> = withContext(Dispatchers.IO) {
        Log.v("CONSOLE", showCurrentThreadInfo())
        dishDao.getAllWithCoroutines().map {
            it.toDish()
        }
    }

    override suspend fun getAll(category: Int): List<Dish> = withContext(Dispatchers.IO) {
        Log.v("CONSOLE", showCurrentThreadInfo())
        dishDao.getAllByCategoryWithCoroutines(category).map {
            it.toDish()
        }
    }

    override suspend fun getAllFavorites(): List<Dish> = withContext(Dispatchers.IO) {
        dishDao.getAllFavorites().map {
            it.toDish()
        }
    }

    override suspend fun getAllFavorites(category: Int): List<Dish> = withContext(Dispatchers.IO) {
        dishDao.getAllFavoritesByCategory(category).map {
            it.toDish()
        }
    }

    override suspend fun favorite(dishId: Int, isFavorite: Boolean)  = withContext(Dispatchers.IO) {
        val favorite = if(isFavorite) 1 else 0
        dishDao.favorite(dishId,favorite)
    }
}