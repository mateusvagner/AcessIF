package com.mv.acessif.presentation.auth.commonState

import androidx.annotation.StringRes
import com.mv.acessif.R

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
