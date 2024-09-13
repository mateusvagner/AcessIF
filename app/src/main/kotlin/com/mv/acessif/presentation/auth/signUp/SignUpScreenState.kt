package com.mv.acessif.presentation.auth.signUp

import com.mv.acessif.presentation.UiText
import com.mv.acessif.presentation.auth.commonState.EmailState
import com.mv.acessif.presentation.auth.commonState.NameState
import com.mv.acessif.presentation.auth.commonState.PasswordState

data class SignUpScreenState(
    val nameTextFieldState: NameState = NameState(),
    val emailTextFieldState: EmailState = EmailState(),
    val passwordTextFieldState: PasswordState = PasswordState(),
    val isLoading: Boolean = false,
    val signUpError: UiText? = null,
)
