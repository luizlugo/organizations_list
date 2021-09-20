package com.volcanolabs.githuborganizations.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object InternalStorageModule {
    private const val INTERNAL_STORAGE_KEY = "mx.volcanolabs.githuborganizations.PREFERENCES_FILE_KEY"
    @Provides
    @Singleton
    fun provideSharedPreferences(context: Application): SharedPreferences {
        return context.getSharedPreferences(INTERNAL_STORAGE_KEY, Context.MODE_PRIVATE)
    }
}