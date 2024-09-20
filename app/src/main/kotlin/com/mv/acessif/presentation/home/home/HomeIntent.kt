package com.mv.acessif.presentation.home.home

sealed interface HomeIntent {
    data object OnNewTranscription : HomeIntent

    data object OnMyTranscriptions : HomeIntent

    data object OnLogout : HomeIntent
}
