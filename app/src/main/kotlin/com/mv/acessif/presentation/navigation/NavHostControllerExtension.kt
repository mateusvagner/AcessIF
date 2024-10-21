package com.mv.acessif.presentation.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.mv.acessif.presentation.navigation.model.NavigationAction
import com.mv.acessif.presentation.util.collectWithLifecycle

/**
 * Observes and processes navigation events from a [Navigator].
 *
 * This extension function for [NavHostController] collects navigation actions
 * from a [Navigator] and executes them accordingly. It handles different types
 * of navigation actions, including navigation to a specific destination,
 * navigation to a top-level destination, and upward navigation in the hierarchy.
 *
 * @param navigator The [Navigator] from which to collect navigation actions.
 */
@SuppressLint("ComposableNaming")
@Composable
fun NavHostController.observeNavigationEvents(navigator: Navigator) {
    navigator.navigationActions.collectWithLifecycle { action ->
        when (action) {
            is NavigationAction.Navigate ->
                navigate(action.destination) {
                    action.navOptions(this)
                }

            is NavigationAction.NavigateUp -> navigateUp()
        }
    }
}
