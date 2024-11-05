package com.mv.acessif.presentation.auth.commonState

data class NameState(
    override val value: String = "",
    override val errorMessage: Int? = null,
) : FormFieldState<NameValidator> {
    override fun validate(validator: NameValidator): NameState {
        return copy(errorMessage = validator.validate(value))
    }
}
