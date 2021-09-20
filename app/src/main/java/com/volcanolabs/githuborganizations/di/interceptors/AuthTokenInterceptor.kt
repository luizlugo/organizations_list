package com.volcanolabs.githuborganizations.di.interceptors

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.volcanolabs.githuborganizations.Constants
import com.volcanolabs.githuborganizations.presentation.ui.login.LoginActivity
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthTokenInterceptor @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val context: Application
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authToken = sharedPreferences.getString(Constants.AUTH_TOKEN_KEY, "")
        val builder = request.newBuilder()

        // Add auth token header
        if (authToken != null) {
            builder.addHeader("Authorization", "token $authToken")
        }

        val response = chain.proceed(builder.build())
        val responseCode = response.code

        if (responseCode == 404 || responseCode == 403 || responseCode == 401) {
            moveUserToLogin()
        }

        return response
    }

    private fun moveUserToLogin() {
        val loginIntent = Intent(context, LoginActivity::class.java)
        loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(loginIntent)
    }
}