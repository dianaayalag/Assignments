package com.emedinaa.kotlinapp.storage.remote

import com.emedinaa.kotlinapp.message.OperationResult
import com.emedinaa.kotlinapp.model.Session
import com.emedinaa.kotlinapp.storage.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRemoteDatasource(
    private val apiClient: ApiClient
) : AuthRepository {

    override suspend fun login(username: String, password: String):
            OperationResult<Session> = withContext(Dispatchers.IO) {
        try {
            val response =
                apiClient.build(username, password).login()

            if (response.isSuccessful) { //200 300 400
                val responseBody = response.body()
                val session = Session(
                    responseBody?.token ?: "",
                    responseBody?.client ?: ""
                )
                OperationResult.Success(session)
            } else {
                OperationResult.Failure(
                    Exception(response.errorBody()?.string())
                )
            }
        } catch (exception: Exception) {
            OperationResult.Failure(exception)
        }
    }
}