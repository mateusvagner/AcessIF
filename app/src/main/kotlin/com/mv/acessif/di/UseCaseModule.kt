package com.mv.acessif.di

import com.mv.acessif.domain.useCase.LoginUseCase
import com.mv.acessif.domain.useCase.RefreshTokenUseCase
import com.mv.acessif.domain.useCase.SignUpUseCase
import com.mv.acessif.domain.useCase.impl.LoginUseCaseImpl
import com.mv.acessif.domain.useCase.impl.RefreshTokenUseCaseImpl
import com.mv.acessif.domain.useCase.impl.SignUpUseCaseImpl
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

    @Binds
    @Singleton
    abstract fun bindsSignupUseCase(impl: SignUpUseCaseImpl): SignUpUseCase

    @Binds
    @Singleton
    abstract fun bindsRefreshTokenUseCase(impl: RefreshTokenUseCaseImpl): RefreshTokenUseCase
}
