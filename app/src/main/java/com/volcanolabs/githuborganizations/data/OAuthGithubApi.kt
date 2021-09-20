package com.volcanolabs.githuborganizations.data

import com.volcanolabs.githuborganizations.data.models.GithubAccessToken
import retrofit2.http.GET
import retrofit2.http.Query

interface OAuthGithubApi {
    @GET("login/oauth/access_token")
    suspend fun getAccessToken(
        @Query("client_id") clientId: String,
        @Query("client_secret") clientSecret: String,
        @Query("code") code: String) : GithubAccessToken
}