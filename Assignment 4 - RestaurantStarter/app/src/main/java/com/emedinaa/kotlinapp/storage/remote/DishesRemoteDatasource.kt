package com.emedinaa.kotlinapp.storage.remote

import com.emedinaa.kotlinapp.message.OperationResult
import com.emedinaa.kotlinapp.model.Dish
import com.emedinaa.kotlinapp.storage.DishesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DishesRemoteDatasource(
    private val apiClient: ApiClient
) : DishesRepository {

    override suspend fun getAll(token: String):
            OperationResult<List<Dish>> = withContext(Dispatchers.IO) {
        try {
            val response =
                apiClient.build().dishes(token)

            if (response.isSuccessful) { //200 300 400
                val responseBody = response.body()
                val dishes = responseBody?.map {
                    it.toDish()
                } ?: emptyList()
                OperationResult.Success(dishes)
            } else {
                OperationResult.Failure(
                    Exception(response.errorBody()?.string())
                )
            }
        } catch (exception: Exception) { //500
            OperationResult.Failure(exception)
        }

    }

    override suspend fun getAll(token: String, category: String):
            OperationResult<List<Dish>> = withContext(Dispatchers.IO) {
        try {
            val response =
                apiClient.build().dishesByCategory(token,category)

            if (response.isSuccessful) { //200 300 400
                val responseBody = response.body()
                val dishes = responseBody?.map {
                    it.toDish()
                } ?: emptyList()
                OperationResult.Success(dishes)
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