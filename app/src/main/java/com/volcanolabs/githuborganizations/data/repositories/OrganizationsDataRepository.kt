package com.volcanolabs.githuborganizations.data.repositories

import com.volcanolabs.githuborganizations.Constants
import com.volcanolabs.githuborganizations.data.GithubApi
import com.volcanolabs.githuborganizations.data.OAuthGithubApi
import com.volcanolabs.githuborganizations.data.models.Organization
import timber.log.Timber
import javax.inject.Inject

class OrganizationsDataRepository @Inject constructor(
    private val githubApi: GithubApi
) : OrganizationsRepository {
    override suspend fun getOrganizations(numberOrganizations: Int) : List<Organization> {
        try {
            return githubApi.getOrganizations(numberOrganizations)
        } catch (e: Exception) {
            Timber.d(e)
        }

        return emptyList()
    }
}