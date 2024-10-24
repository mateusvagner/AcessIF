package com.mv.acessif.presentation.home.home

import com.mv.acessif.domain.Transcription

sealed interface HomeIntent {
    data object OnNewTranscription : HomeIntent

    data object OnMyTranscriptions : HomeIntent

    data class OnTranscriptionPressed(val transcription: Transcription) : HomeIntent

    data object OnReloadScreen : HomeIntent

    data object OnLogout : HomeIntent

    data object OnAboutTheProject : HomeIntent

    data object OnContactUs : HomeIntent
}
