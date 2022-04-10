package com.emedinaa.kotlinapp.storage.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DishDao {

    //query
    @Query("SELECT * from tb_dish")
    fun getAll():List<DishDTO>

    @Query("SELECT * from tb_dish")
    suspend fun getAllWithCoroutines():List<DishDTO>

    @Query("SELECT * from tb_dish where favorite = 1")
    suspend fun getAllFavorites():List<DishDTO>

    @Query("SELECT * from tb_dish where category =:category")
    fun getAllByCategory(category:Int):List<DishDTO>

    @Query("SELECT * from tb_dish where category =:category")
    suspend fun getAllByCategoryWithCoroutines(category:Int):List<DishDTO>

    @Query("SELECT * from tb_dish where category =:category AND favorite = 1")
    suspend fun getAllFavoritesByCategory(category:Int):List<DishDTO>

    //insert
    @Insert
    fun insert(dishes:List<DishDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dish:DishDTO)

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(dish:DishDTO)

    @Query("UPDATE tb_dish SET favorite =:favorite WHERE id = :dishId")
    suspend fun favorite(dishId:Int, favorite:Int)

    //delete
    @Delete
    fun delete(dish:DishDTO)

}