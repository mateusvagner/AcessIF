package com.mv.acessif.presentation.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mv.acessif.presentation.auth.login.loginRoute
import com.mv.acessif.presentation.auth.signUp.signUpRoute
import com.mv.acessif.presentation.home.home.homeGraphRoute
import com.mv.acessif.presentation.navigation.model.Destination
import com.mv.acessif.presentation.root.demoTranscription.demoTranscriptionRoute
import com.mv.acessif.presentation.root.welcome.welcomeRoute
import kotlinx.serialization.Serializable

sealed interface RootGraph : Destination {
    @Serializable
    data object WelcomeRoute : RootGraph

    @Serializable
    data object DemoTranscriptionRoute : RootGraph

    @Serializable
    data object LoginRoute : RootGraph

    @Serializable
    data object SignUpRoute : RootGraph

    @Serializable
    data object HomeGraph : RootGraph
}

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    startDestination: Destination,
    navController: NavHostController,
) {
    NavHost(
        startDestination = startDestination,
        navController = navController,
    ) {
        welcomeRoute(
            modifier = modifier,
            navController = navController,
        )

        demoTranscriptionRoute(
            modifier = modifier,
            navController = navController,
        )

        loginRoute(
            modifier = modifier,
            navController = navController,
        )

        signUpRoute(
            modifier = modifier,
            navController = navController,
        )

        homeGraphRoute(
            modifier = modifier,
            navController = navController,
        )
    }
}
