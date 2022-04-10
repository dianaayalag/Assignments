package com.emedinaa.kotlinapp.storage.remote

import com.emedinaa.kotlinapp.message.OperationResult
import com.emedinaa.kotlinapp.model.Category
import com.emedinaa.kotlinapp.model.Session
import com.emedinaa.kotlinapp.storage.AuthRepository
import com.emedinaa.kotlinapp.storage.CategoriesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class CategoriesRemoteDatasource(
    private val apiClient: ApiClient
) : CategoriesRepository {

    override suspend fun getAll(token:String):
            OperationResult<List<Category>> = withContext(Dispatchers.IO) {
        try {
            val response =
                apiClient.build().categories(token)

            if (response.isSuccessful) { //200 300 400
                val responseBody = response.body()
                val categories = responseBody?.map {
                    it.toCategory()
                }?: emptyList()
                OperationResult.Success(categories)
            } else {
                OperationResult.Failure(
                    Exception(response.errorBody()?.string())
                )
            }
        } catch (exception: Exception) { //500
            OperationResult.Failure(exception)
        }
    }

    override suspend fun addCategory(
        token: String,
        name: String
    ): OperationResult<Category> = withContext(Dispatchers.IO)  {
        try {
            val categoryJSON = JSONObject()
            categoryJSON.put("name", name)
            categoryJSON.put("description", name)
            val categoryJSONString = categoryJSON.toString()
            val requestBody = categoryJSONString.toRequestBody("application/json".toMediaType())

            val response =
                apiClient.build().addCategory(token, requestBody)

            if (response.isSuccessful) { //200 300 400
                val responseBody = response.body()
                val category = responseBody?.toCategory()
                OperationResult.Success(category!!)
            } else {
                OperationResult.Failure(
                    Exception(response.errorBody()?.string())
                )
            }
        } catch (exception: Exception) { //500
            OperationResult.Failure(exception)
        }
    }

    override suspend fun updateCategory(
        token: String,
        id: String,
        name: String
    ): OperationResult<String> = withContext(Dispatchers.IO)  {
        try {
            val categoryJSON = JSONObject()
            categoryJSON.put("_id", id)
            categoryJSON.put("name", name)
            categoryJSON.put("description", name)
            val categoryJSONString = categoryJSON.toString()
            val requestBody = categoryJSONString.toRequestBody("application/json".toMediaType())

            val response =
                apiClient.build().updateCategory(token, requestBody)

            if (response.isSuccessful) { //200 300 400
                val responseBody = response.body()
                OperationResult.Success(responseBody.toString())
            } else {
                OperationResult.Failure(
                    Exception(response.errorBody()?.string())
                )
            }
        } catch (exception: Exception) { //500
            OperationResult.Failure(exception)
        }
    }

    override suspend fun deleteCategory(
        token: String,
        id: String
    ): OperationResult<String> = withContext(Dispatchers.IO)  {
        try {
            val response =
                apiClient.build().deleteCategory(token, id)

            if (response.isSuccessful) { //200 300 400
                val responseBody = response.body()
                OperationResult.Success(responseBody.toString())
            } else {
                OperationResult.Failure(
                    Exception(response.errorBody()?.string())
                )
            }
        } catch (exception: Exception) { //500
            OperationResult.Failure(exception)
        }
    }
}