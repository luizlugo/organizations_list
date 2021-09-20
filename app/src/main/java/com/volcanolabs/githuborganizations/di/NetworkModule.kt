package com.volcanolabs.githuborganizations.di

import androidx.viewbinding.BuildConfig
import com.volcanolabs.githuborganizations.data.GithubApi
import com.volcanolabs.githuborganizations.data.OAuthGithubApi
import com.volcanolabs.githuborganizations.di.interceptors.AuthTokenInterceptor
import com.volcanolabs.githuborganizations.di.interceptors.HeadersInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OAuthGithubRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GithubRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RegularOkHttpClient

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private val oauth_base_path = "https://github.com"
    private val api_base_path = "https://api.github.com"

    @RegularOkHttpClient
    @Provides
    @Singleton
    fun provideOkHttpClient(headersInterceptor: HeadersInterceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { message: String? ->
                Timber.tag("OkHttpClient").i(message)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            httpClient.addInterceptor(loggingInterceptor)
        }

        httpClient.addInterceptor(headersInterceptor)

        return httpClient.build()
    }

    @AuthOkHttpClient
    @Provides
    @Singleton
    fun provideAuthOkHttpClient(
        headersInterceptor: HeadersInterceptor,
        authTokenInterceptor: AuthTokenInterceptor
    ): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor { message: String? ->
                Timber.tag("OkHttpClient").i(message)
            }
            loggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
            httpClient.addInterceptor(loggingInterceptor)
        }

        httpClient.addInterceptor(headersInterceptor)
        httpClient.addInterceptor(authTokenInterceptor)

        return httpClient.build()
    }

    @OAuthGithubRetrofit
    @Provides
    @Singleton
    fun providesOAuthRetrofit(@RegularOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(oauth_base_path)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @GithubRetrofit
    @Provides
    @Singleton
    fun providesGithubRetrofit(@AuthOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(api_base_path)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOAuthGithubApiService(@OAuthGithubRetrofit retrofit: Retrofit): OAuthGithubApi {
        return retrofit
            .create(OAuthGithubApi::class.java)
    }

    @Provides
    @Singleton
    fun provideGithubApiService(@GithubRetrofit retrofit: Retrofit): GithubApi {
        return retrofit
            .create(GithubApi::class.java)
    }
}