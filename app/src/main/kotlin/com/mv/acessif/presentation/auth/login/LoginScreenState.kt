package com.mv.acessif.presentation.auth.login

import com.mv.acessif.presentation.UiText
import com.mv.acessif.presentation.auth.commonState.EmailState
import com.mv.acessif.presentation.auth.commonState.PasswordState

data class LoginScreenState(
    val emailTextFieldState: EmailState = EmailState(),
    val passwordTextFieldState: PasswordState = PasswordState(),
    val isLoading: Boolean = false,
    val signinError: UiText? = null,
)
