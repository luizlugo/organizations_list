package com.volcanolabs.githuborganizations.data.repositories

import com.volcanolabs.githuborganizations.data.models.Organization

interface OrganizationsRepository {
    suspend fun getOrganizations(numberOrganizations: Int) : List<Organization>
}