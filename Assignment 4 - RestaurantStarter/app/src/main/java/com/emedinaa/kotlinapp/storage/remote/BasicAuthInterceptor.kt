package com.emedinaa.kotlinapp.storage.remote

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response


class BasicAuthInterceptor(username:String,password:String): Interceptor {

    private val basicCredentials = Credentials.basic(username,password)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization",basicCredentials).build()
        return chain.proceed(request)
    }
}