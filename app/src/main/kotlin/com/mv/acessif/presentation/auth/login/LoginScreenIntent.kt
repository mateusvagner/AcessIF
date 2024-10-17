package com.mv.acessif.presentation.auth.login

sealed interface LoginScreenIntent {
    data class OnEmailChanged(val email: String) : LoginScreenIntent

    data class OnPasswordChanged(val password: String) : LoginScreenIntent

    data object OnTogglePasswordVisibility : LoginScreenIntent

    data object OnSigninPressed : LoginScreenIntent

    data object OnSignUpPressed : LoginScreenIntent

    data object OnNavigateBack : LoginScreenIntent

    data object OnTryAgain : LoginScreenIntent
}
