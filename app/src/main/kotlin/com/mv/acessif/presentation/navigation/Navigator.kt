package com.mv.acessif.presentation.navigation

import androidx.navigation.NavOptionsBuilder
import com.mv.acessif.presentation.navigation.model.Destination
import com.mv.acessif.presentation.navigation.model.NavigationAction
import kotlinx.coroutines.flow.Flow

interface Navigator {
    val startDestination: Destination
    val navigationActions: Flow<NavigationAction>

    suspend fun navigateTo(destination: Destination)

    suspend fun navigateTo(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit,
    )

    suspend fun navigateUp()

    fun tryNavigateTo(destination: Destination)

    fun tryNavigateTo(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit,
    )

    fun tryNavigateUp()
}
