package com.mv.acessif.di

import android.app.Application
import androidx.annotation.OptIn
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import com.mv.acessif.local.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PlayerModule {
    @OptIn(UnstableApi::class)
    @Provides
    @ViewModelScoped
    fun providePlayer(
        app: Application,
        sharedPreferencesManager: SharedPreferencesManager,
    ): Player {
        val dataSourceFactory =
            DefaultHttpDataSource.Factory().apply {
                setDefaultRequestProperties(
                    mapOf(
                        "Authorization" to "Bearer ${
                            sharedPreferencesManager.getAccessToken().orEmpty()
                        }",
                    ),
                )
            }

        return ExoPlayer.Builder(app)
            .setMediaSourceFactory(DefaultMediaSourceFactory(dataSourceFactory))
            .build()
    }
}
