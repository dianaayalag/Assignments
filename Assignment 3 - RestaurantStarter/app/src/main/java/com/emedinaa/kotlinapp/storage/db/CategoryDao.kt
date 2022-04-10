package com.emedinaa.kotlinapp.storage.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CategoryDao {

    //query
    @Query("SELECT * from tb_category")
    fun getAll():List<CategoryDTO>

    //insert
    @Insert
    fun insert(categories:List<CategoryDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(category:CategoryDTO)

    //update
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(category:CategoryDTO)

    //delete
    @Query("DELETE from tb_category WHERE id = :id")
    fun delete(id:Int)

}