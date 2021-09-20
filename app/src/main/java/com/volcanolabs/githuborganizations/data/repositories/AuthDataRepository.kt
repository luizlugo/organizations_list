package com.volcanolabs.githuborganizations.data.repositories

import com.volcanolabs.githuborganizations.Constants
import com.volcanolabs.githuborganizations.data.OAuthGithubApi
import javax.inject.Inject

class AuthDataRepository @Inject constructor(
    private val githubApi: OAuthGithubApi
) : AuthRepository {
    override suspend fun getAccessToken(code: String) =
        githubApi.getAccessToken(
            Constants.GITHUB_CLIENT_ID,
            Constants.GITHUB_CLIENT_SECRET,
            code
        )
}