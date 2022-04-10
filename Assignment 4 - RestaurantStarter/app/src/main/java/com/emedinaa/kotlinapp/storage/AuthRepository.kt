package com.emedinaa.kotlinapp.storage

import com.emedinaa.kotlinapp.message.OperationResult
import com.emedinaa.kotlinapp.model.Session

interface AuthRepository {

    suspend fun login(username:String,
    password:String):OperationResult<Session> //Pair<Any,Exception>
}