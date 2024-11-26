package com.mv.acessif.presentation.auth.commonState

data class PasswordState(
    override val value: String = "",
    override val errorMessage: Int? = null,
    val isVisible: Boolean = false,
) : FormFieldState<PasswordValidator> {
    override fun validate(validator: PasswordValidator): PasswordState {
        return copy(errorMessage = validator.validate(value))
    }
}
