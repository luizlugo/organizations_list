package com.volcanolabs.githuborganizations.di

import com.volcanolabs.githuborganizations.data.repositories.AuthDataRepository
import com.volcanolabs.githuborganizations.data.repositories.AuthRepository
import com.volcanolabs.githuborganizations.data.repositories.OrganizationsDataRepository
import com.volcanolabs.githuborganizations.data.repositories.OrganizationsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ApplicationRepositoriesModule {
    @Binds
    abstract fun bindOrganizationsRepository(organizationsRepository: OrganizationsDataRepository): OrganizationsRepository
    @Binds
    abstract fun bindOAuthRepository(authDataRepository: AuthDataRepository): AuthRepository
}