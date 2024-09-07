package com.mv.acessif.di

import com.mv.acessif.domain.useCase.LoginUseCase
import com.mv.acessif.domain.useCase.impl.LoginUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {
    @Binds
    @Singleton
    abstract fun bindsLoginUseCase(impl: LoginUseCaseImpl): LoginUseCase
}
