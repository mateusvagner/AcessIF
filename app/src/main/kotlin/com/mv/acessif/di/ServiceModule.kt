package com.mv.acessif.di

import com.mv.acessif.network.service.AudioFileService
import com.mv.acessif.network.service.AuthService
import com.mv.acessif.network.service.SummaryService
import com.mv.acessif.network.service.TranscriptionService
import com.mv.acessif.network.service.impl.KtorAudioFileService
import com.mv.acessif.network.service.impl.KtorAuthService
import com.mv.acessif.network.service.impl.KtorSummaryService
import com.mv.acessif.network.service.impl.KtorTranscriptionService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule {
    @Binds
    @Singleton
    abstract fun bindsAudioFileService(impl: KtorAudioFileService): AudioFileService

    @Binds
    @Singleton
    abstract fun bindsAuthService(impl: KtorAuthService): AuthService

    @Binds
    @Singleton
    abstract fun bindsSummaryService(impl: KtorSummaryService): SummaryService

    @Binds
    @Singleton
    abstract fun bindsTranscriptionService(impl: KtorTranscriptionService): TranscriptionService
}
