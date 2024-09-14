package com.mv.acessif.presentation.auth.commonState

import androidx.annotation.StringRes
import com.mv.acessif.R

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
