package com.mv.acessif.di

import com.mv.acessif.local.SharedPreferencesManager
import com.mv.acessif.network.HttpClientFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient

@Module
@InstallIn(SingletonComponent::class)
object HttpClientModule {
    @Provides
    fun provideHttpClient(sharedPreferencesManager: SharedPreferencesManager): HttpClient {
        return HttpClientFactory.create(sharedPreferencesManager)
    }
}
