package com.emedinaa.kotlinapp.storage.remote

import com.emedinaa.kotlinapp.model.Category
import com.emedinaa.kotlinapp.model.Dish
import com.google.gson.annotations.SerializedName

/*
{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJkYXRhIjp7ImF2YXRhciI6ImRlZmF1bHQuanBnIiwiX2lkIjoiNWY1NmU3NDhkYWNiN2I4YWQyMmY2MTYxIiwibm9tYnJlcyI6IktlbHZpbiIsImFwZWxsaWRvcyI6IkNhcnJpb24iLCJlbWFpbCI6ImNsaWVudGVAZ21haWwuY29tIn0sImlhdCI6MTY0NzkxNjQ0MH0.5IZH453Kta5_mYV_SImjb7XDb8r4fZg3H-xJrHoiXgU",
    "client_id": "5f56e748dacb7b8ad22f6161"
}
 */

data class SessionDTO(
    val token:String?,
    @SerializedName("client_id") val client:String?
)

/*
[
    {
        "active": true,
        "_id": "5f53d5bb05709ecaa3c93b42",
        "name": "Entradas",
        "description": "Todos los platos de entradas."
    },
 */
data class CategoryDTO(
    val active:String?,
    @SerializedName("_id") val id:String?,
    val name:String?,
    val description:String?
) {
    fun toCategory() = Category(id?:"",name?:"",description?:"")
}

/*
[
    {
        "_id": "5f565ad505709ecaa3c93b4d",
        "category": {
            "_id": "5f53d5ce05709ecaa3c93b43",
            "name": "Postres"
        },
        "name": "Suspiro a la limeña",
        "avatar": "1599494869539.jpg",
        "price": 13,
        "description": "También conocido como Suspiro Limeño, esta delicia está en el top de los postres más dulces que he probado. En realidad es tan dulce que siempre lo acompaño con un gran vaso de agua helada para bajar su intenso dulzor. También va muy bien con té o con una copita de Pisco (nuestro famoso destilado de uvas). ",
        "active": true
    },
 */

data class DishDTO(
    @SerializedName("_id") val id:String?,
    val category:CategoryDTO?,
    val name:String?,
    @SerializedName("avatar") val photo:String?,
    val price:String?,
    val description:String?,
    val active:String?
) {
    fun toDish() = Dish(id?:"",category?.id?:"",name?:"",photo?:"",price?:"",description?:"",0)
}
