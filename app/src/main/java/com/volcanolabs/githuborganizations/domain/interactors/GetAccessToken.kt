package com.volcanolabs.githuborganizations.domain.interactors

import com.volcanolabs.githuborganizations.data.models.GithubAccessToken
import com.volcanolabs.githuborganizations.data.repositories.AuthRepository
import javax.inject.Inject

class GetAccessToken @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun execute(code: String): GithubAccessToken {
        return authRepository.getAccessToken(code)
    }
}