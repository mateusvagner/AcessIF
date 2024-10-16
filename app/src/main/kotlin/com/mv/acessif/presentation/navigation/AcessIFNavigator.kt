package com.mv.acessif.presentation.navigation

import androidx.navigation.NavOptionsBuilder
import com.mv.acessif.presentation.navigation.model.Destination
import com.mv.acessif.presentation.navigation.model.NavigationAction
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class AcessIFNavigator(
    override val startDestination: Destination,
) : Navigator {
    private val _navigationActions = Channel<NavigationAction>()

    override val navigationActions = _navigationActions.receiveAsFlow()

    override suspend fun navigateTo(destination: Destination) {
        _navigationActions.send(NavigationAction.Navigate(destination))
    }

    override fun tryNavigateTo(destination: Destination) {
        _navigationActions.trySend(NavigationAction.Navigate(destination))
    }

    override suspend fun navigateTo(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit,
    ) {
        _navigationActions.send(NavigationAction.Navigate(destination, navOptions))
    }

    override fun tryNavigateTo(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit,
    ) {
        _navigationActions.trySend(NavigationAction.Navigate(destination, navOptions))
    }

    override suspend fun navigateToTopLevel(destination: Destination) {
        _navigationActions.send(NavigationAction.NavigateToTopLevel(destination))
    }

    override fun tryNavigateToTopLevel(destination: Destination) {
        _navigationActions.trySend(NavigationAction.NavigateToTopLevel(destination))
    }

    override suspend fun navigateUp() {
        _navigationActions.send(NavigationAction.NavigateUp)
    }

    override fun tryNavigateUp() {
        _navigationActions.trySend(NavigationAction.NavigateUp)
    }
}
