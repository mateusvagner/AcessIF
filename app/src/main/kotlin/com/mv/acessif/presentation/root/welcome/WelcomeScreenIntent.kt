package com.mv.acessif.presentation.root.welcome

sealed interface WelcomeScreenIntent {
    data object OnTranscriptPressed : WelcomeScreenIntent

    data object OnLoginPressed : WelcomeScreenIntent

    data object OnSignupPressed : WelcomeScreenIntent
}
