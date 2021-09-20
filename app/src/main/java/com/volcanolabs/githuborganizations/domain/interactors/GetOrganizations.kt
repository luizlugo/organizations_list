package com.volcanolabs.githuborganizations.domain.interactors

import com.volcanolabs.githuborganizations.data.models.Organization
import com.volcanolabs.githuborganizations.data.repositories.OrganizationsRepository
import javax.inject.Inject

class GetOrganizations @Inject constructor(
    private val organizationsRepository: OrganizationsRepository
) {
    suspend fun execute(numberOrganizations: Int): List<Organization> {
        return organizationsRepository.getOrganizations(numberOrganizations)
    }
}