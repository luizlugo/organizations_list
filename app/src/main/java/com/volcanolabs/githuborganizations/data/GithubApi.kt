package com.volcanolabs.githuborganizations.data

import com.volcanolabs.githuborganizations.data.models.Organization
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("organizations")
    suspend fun getOrganizations(@Query("per_page") numberOrganizations: Int) : List<Organization>
}