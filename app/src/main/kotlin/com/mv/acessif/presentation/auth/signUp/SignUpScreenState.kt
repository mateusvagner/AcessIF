package com.mv.acessif.presentation.auth.signUp

import androidx.annotation.StringRes
import com.mv.acessif.R
import com.mv.acessif.presentation.UiText

data class SignUpScreenState(
    val nameTextFieldState: NameState = NameState(),
    val emailTextFieldState: EmailState = EmailState(),
    val passwordTextFieldState: PasswordState = PasswordState(),
    val signUpError: UiText? = null,
)

data class NameState(
    val value: String = "",
    val isError: Boolean = false,
    val nameError: NameError? = null,
)

enum class NameError(
    @StringRes val errorMessage: Int,
) {
    EMPTY(R.string.name_is_required),
    SHORT(R.string.name_is_too_short),
}

data class EmailState(
    val email: String = "",
    val isError: Boolean = false,
    val emailError: EmailError? = null,
)

enum class EmailError(
    @StringRes val errorMessage: Int,
) {
    EMPTY(R.string.email_is_required),
    INVALID(R.string.email_is_invalid),
}

data class PasswordState(
    val password: String = "",
    val isVisible: Boolean = false,
    val isError: Boolean = false,
    val passwordError: PasswordError? = null,
)

enum class PasswordError(
    @StringRes val errorMessage: Int,
) {
    EMPTY(R.string.password_is_required),
}
