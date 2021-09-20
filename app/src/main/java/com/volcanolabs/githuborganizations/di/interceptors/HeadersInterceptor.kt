package com.volcanolabs.githuborganizations.di.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HeadersInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request
            .newBuilder()
            .addHeader("accept", "application/json")
            .build()
        return chain.proceed(newRequest)
    }
}