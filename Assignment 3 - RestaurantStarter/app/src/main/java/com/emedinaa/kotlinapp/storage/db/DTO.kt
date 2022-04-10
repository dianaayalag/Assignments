package com.emedinaa.kotlinapp.storage.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.emedinaa.kotlinapp.model.Category
import com.emedinaa.kotlinapp.model.Dish

@Entity(tableName = "tb_category")
data class CategoryDTO(
    @ColumnInfo(name="name") val name: String?,
    @ColumnInfo(name="desc") val description: String?
) {
   @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    constructor(id: Int, name: String?, description: String?) : this(name, description) {
        this.id = id
    }

    fun toCategory() = Category(
        id ?: -1,
        name ?: "",
        description ?: ""
    )
}

@Entity(tableName = "tb_dish")
data class DishDTO(
    val category: Int,
    val name: String?,
    val photo: String?,
    val price: String?,
    val desc: String?,
    val favorite:Int? = 0
) {
    @PrimaryKey(autoGenerate = true)
    var id:Int? = null

    fun toDish() = Dish(
        id ?: -1,
        category,
        name?: "",
        photo?: "",
        price?: "",
        desc?: "",
        favorite?:0
    )
}

/*
@Parcelize
data class Category(
    val id: Int,
    val name: String?,
    val desc: String?
) : Parcelable

@Parcelize
data class Dish(
    val id: Int,
    val category: Int,
    val name: String,
    val photo: String,
    val price: String,
    val desc: String
) : Parcelable
 */