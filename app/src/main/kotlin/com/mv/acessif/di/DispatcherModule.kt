package com.mv.acessif.di

import com.mv.acessif.util.DefaultDispatcherProvider
import com.mv.acessif.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Singleton
    @Provides
    fun providesDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider
}
