package com.mv.acessif.presentation.auth.commonState

import androidx.annotation.StringRes
import com.mv.acessif.R

interface FormFieldValidator {
    fun validate(value: String): Int?
}

object EmailValidator: FormFieldValidator {
    override fun validate(value: String): Int? {
        return when {
            value.isEmpty() -> FormFieldError.EMAIL_EMPTY.errorMessage
            !android.util.Patterns.EMAIL_ADDRESS.matcher(value)
                .matches() -> FormFieldError.EMAIL_INVALID.errorMessage

            else -> null
        }
    }
}


object NameValidator: FormFieldValidator {
    override fun validate(value: String): Int? {
        return when {
            value.isEmpty() -> FormFieldError.NAME_EMPTY.errorMessage
            value.length < 3 -> FormFieldError.NAME_SHORT.errorMessage
            else -> null
        }
    }
}

object PasswordValidator: FormFieldValidator {
    override fun validate(value: String): Int? {
        return if (value.isEmpty()) {
            FormFieldError.PASSWORD_EMPTY.errorMessage
        } else {
            null
        }
    }
}

enum class FormFieldError(
    @StringRes val errorMessage: Int,
) {
    NAME_EMPTY(R.string.name_is_required),
    NAME_SHORT(R.string.name_is_too_short),
    EMAIL_EMPTY(R.string.email_is_required),
    EMAIL_INVALID(R.string.email_is_invalid),
    PASSWORD_EMPTY(R.string.password_is_required),
}


