package com.mv.acessif.presentation.root

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mv.acessif.presentation.auth.login.loginScreen
import com.mv.acessif.presentation.auth.signUp.signUpScreen
import com.mv.acessif.presentation.home.home.homeNavGraph
import com.mv.acessif.presentation.root.demoTranscription.demoTranscriptionScreen
import com.mv.acessif.presentation.root.welcome.WelcomeScreen
import com.mv.acessif.presentation.root.welcome.welcomeScreen

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    rootNavController: NavHostController,
) {
    Scaffold { contentPadding ->
        NavHost(
            startDestination = WelcomeScreen,
            navController = rootNavController,
        ) {
            welcomeScreen(modifier = modifier.padding(contentPadding), rootNavController = rootNavController)

            demoTranscriptionScreen(modifier = modifier.padding(contentPadding), rootNavController = rootNavController)

            loginScreen(modifier = modifier.padding(contentPadding), rootNavController = rootNavController)

            signUpScreen(modifier = modifier.padding(contentPadding), rootNavController = rootNavController)

            homeNavGraph(modifier = modifier, rootNavController = rootNavController)
        }
    }
}
