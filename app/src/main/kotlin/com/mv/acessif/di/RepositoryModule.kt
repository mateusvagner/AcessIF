package com.mv.acessif.di

import com.mv.acessif.data.repository.AudioFileRepositoryImpl
import com.mv.acessif.data.repository.AuthRepositoryImpl
import com.mv.acessif.data.repository.SharedPreferencesRepositoryImpl
import com.mv.acessif.data.repository.SummaryRepositoryImpl
import com.mv.acessif.data.repository.TranscriptionRepositoryImpl
import com.mv.acessif.domain.repository.AudioFileRepository
import com.mv.acessif.domain.repository.AuthRepository
import com.mv.acessif.domain.repository.SharedPreferencesRepository
import com.mv.acessif.domain.repository.SummaryRepository
import com.mv.acessif.domain.repository.TranscriptionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsAudioFileRepository(impl: AudioFileRepositoryImpl): AudioFileRepository

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsSummaryRepository(impl: SummaryRepositoryImpl): SummaryRepository

    @Binds
    @Singleton
    abstract fun bindsTranscriptionRepository(impl: TranscriptionRepositoryImpl): TranscriptionRepository

    @Binds
    @Singleton
    abstract fun bindsSharedPreferencesRepository(impl: SharedPreferencesRepositoryImpl): SharedPreferencesRepository
}
