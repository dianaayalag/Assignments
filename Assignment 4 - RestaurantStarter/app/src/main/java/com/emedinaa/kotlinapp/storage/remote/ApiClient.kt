package com.emedinaa.kotlinapp.storage.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*


private const val URL = "https://restaurant-administrador-am.herokuapp.com/"

class ApiClient {

    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    fun build(username: String, password: String): Service {
        val httClient = OkHttpClient.Builder().let {
            it.addInterceptor(BasicAuthInterceptor(username, password))
            it.addInterceptor(httpLoggingInterceptor)
            it.build()
        }
        val retrofit = Retrofit.Builder().let {
            it.baseUrl(URL)
            it.addConverterFactory(GsonConverterFactory.create())
            it.client(httClient)
            it.build()
        }
        return retrofit.create(Service::class.java)
    }

    fun build(): Service {
        val httClient = OkHttpClient.Builder().let {
            it.addInterceptor(httpLoggingInterceptor)
            it.build()
        }
        val retrofit = Retrofit.Builder().let {
            it.baseUrl(URL)
            it.addConverterFactory(GsonConverterFactory.create())
            it.client(httClient)
            it.build()
        }

        return retrofit.create(Service::class.java)
    }

    interface Service {

        @POST("auth-client")
        suspend fun login(): Response<SessionDTO>

        @GET("categories")
        suspend fun categories(
            @Header("token") token: String
        ): Response<List<CategoryDTO>>

        @GET("dishes")
        suspend fun dishes(
            @Header("token") token: String
        ): Response<List<DishDTO>>

        @GET("dishes/category/{id}")
        suspend fun dishesByCategory(
            @Header("token") token: String,
            @Path("id") category: String
        ): Response<List<DishDTO>>

        @POST("categories")
        suspend fun addCategory(
            @Header("token") token: String,
            @Body requestBody: RequestBody
        ): Response<CategoryDTO>

        @PUT("categories")
        suspend fun updateCategory(
            @Header("token") token: String,
            @Body requestBody: RequestBody
        ): Response<ResponseBody>

        @DELETE("categories/{id}")
        suspend fun deleteCategory(
            @Header("token") token: String,
            @Path("id") id: String
        ): Response<ResponseBody>

    }
}