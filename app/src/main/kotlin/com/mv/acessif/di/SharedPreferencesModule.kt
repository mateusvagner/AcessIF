package com.mv.acessif.di

import com.mv.acessif.local.SharedPreferencesManager
import com.mv.acessif.local.SharedPreferencesManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SharedPreferencesModule {
    @Binds
    @Singleton
    abstract fun bindsSharedPreferencesManager(impl: SharedPreferencesManagerImpl): SharedPreferencesManager
}
