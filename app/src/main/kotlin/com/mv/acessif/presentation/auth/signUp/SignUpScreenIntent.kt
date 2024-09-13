package com.mv.acessif.presentation.auth.signUp

sealed interface SignUpScreenIntent {
    data class OnNameChanged(val name: String) : SignUpScreenIntent

    data class OnEmailChanged(val email: String) : SignUpScreenIntent

    data class OnPasswordChanged(val password: String) : SignUpScreenIntent

    data object OnTogglePasswordVisibility : SignUpScreenIntent

    data object OnSignupPressed : SignUpScreenIntent

    data object OnNavigateBack : SignUpScreenIntent

    data object OnTryAgain : SignUpScreenIntent
}
