package com.volcanolabs.githuborganizations.data.repositories

import com.volcanolabs.githuborganizations.data.models.GithubAccessToken

interface AuthRepository {
    suspend fun getAccessToken(code: String) : GithubAccessToken
}