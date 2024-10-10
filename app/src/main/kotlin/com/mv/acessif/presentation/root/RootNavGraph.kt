package com.mv.acessif.presentation.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mv.acessif.presentation.auth.login.loginScreen
import com.mv.acessif.presentation.auth.signUp.signUpScreen
import com.mv.acessif.presentation.home.home.homeNavGraph
import com.mv.acessif.presentation.root.demoTranscription.demoTranscriptionScreen
import com.mv.acessif.presentation.root.welcome.welcomeScreen

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    startDestination: RootStartDestination,
    rootNavController: NavHostController,
) {
    NavHost(
        startDestination = startDestination,
        navController = rootNavController,
    ) {
        welcomeScreen(
            modifier = modifier,
            rootNavController = rootNavController,
        )

        demoTranscriptionScreen(
            modifier = modifier,
            rootNavController = rootNavController,
        )

        loginScreen(
            modifier = modifier,
            rootNavController = rootNavController,
        )

        signUpScreen(
            modifier = modifier,
            rootNavController = rootNavController,
        )

        homeNavGraph(
            modifier = modifier,
            rootNavController = rootNavController,
        )
    }
}
