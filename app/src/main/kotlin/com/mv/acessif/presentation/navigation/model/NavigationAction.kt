package com.mv.acessif.presentation.navigation.model

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationAction {
    data class Navigate(
        val destination: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {},
    ) : NavigationAction

    data class NavigateToTopLevel(val destination: Destination) : NavigationAction

    data object NavigateUp : NavigationAction
}
