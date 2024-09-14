package com.mv.acessif.presentation.auth.commonState

import androidx.annotation.StringRes
import com.mv.acessif.R

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
