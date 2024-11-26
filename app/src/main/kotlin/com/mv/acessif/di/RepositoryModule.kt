package com.mv.acessif.di

import com.mv.acessif.data.repository.AudioFileRepository
import com.mv.acessif.data.repository.AuthRepository
import com.mv.acessif.data.repository.FileRepository
import com.mv.acessif.data.repository.SharedPreferencesRepository
import com.mv.acessif.data.repository.SummaryRepository
import com.mv.acessif.data.repository.TranscriptionRepository
import com.mv.acessif.data.repository.UserRepository
import com.mv.acessif.data.repository.impl.AudioFileRepositoryImpl
import com.mv.acessif.data.repository.impl.AuthRepositoryImpl
import com.mv.acessif.data.repository.impl.FileRepositoryImpl
import com.mv.acessif.data.repository.impl.SharedPreferencesRepositoryImpl
import com.mv.acessif.data.repository.impl.SummaryRepositoryImpl
import com.mv.acessif.data.repository.impl.TranscriptionRepositoryImpl
import com.mv.acessif.data.repository.impl.UserRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun bindsUserRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindsFileRepository(impl: FileRepositoryImpl): FileRepository
}
