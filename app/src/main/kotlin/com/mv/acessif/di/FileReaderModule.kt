package com.mv.acessif.di

import com.mv.acessif.util.DefaultFileReader
import com.mv.acessif.util.FileReader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class FileReaderModule {
    @Singleton
    @Binds
    abstract fun bindsFileReader(impl: DefaultFileReader): FileReader
}
