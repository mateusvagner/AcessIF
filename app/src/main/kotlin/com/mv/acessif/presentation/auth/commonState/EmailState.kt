package com.mv.acessif.presentation.auth.commonState

data class EmailState(
    override val value: String = "",
    override val errorMessage: Int? = null,
) : FormFieldState<EmailValidator> {
    override fun validate(validator: EmailValidator): EmailState {
        return copy(errorMessage = validator.validate(value))
    }
}
