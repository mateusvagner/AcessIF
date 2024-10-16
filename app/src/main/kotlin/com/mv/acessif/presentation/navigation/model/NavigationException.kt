package com.mv.acessif.presentation.navigation.model

class NavigationException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Throwable(message, cause)
