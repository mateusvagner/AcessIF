package com.mv.acessif.presentation.auth.commonState

interface FormFieldState<T : FormFieldValidator> {
    val value: String
    val errorMessage: Int?

    fun validate(validator: T): FormFieldState<T>
}
