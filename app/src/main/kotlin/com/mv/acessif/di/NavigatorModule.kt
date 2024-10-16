package com.mv.acessif.di

import com.mv.acessif.presentation.navigation.AcessIFNavigator
import com.mv.acessif.presentation.navigation.Navigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigatorModule {
    @Singleton
    @Provides
    fun providesAppNavigator(): Navigator = AcessIFNavigator()
}
