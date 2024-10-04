package com.mv.acessif

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.mv.acessif.presentation.home.home.HomeNavGraph
import com.mv.acessif.presentation.root.RootNavGraph
import com.mv.acessif.presentation.root.RootStartDestination
import com.mv.acessif.presentation.root.welcome.WelcomeScreen
import com.mv.acessif.ui.theme.AcessIFTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        enableEdgeToEdge()

        setContent {
            if (viewModel.isLoading.value.not()) {
                val startDestination: RootStartDestination =
                    if (viewModel.isLoggedIn.value) {
                        HomeNavGraph
                    } else {
                        WelcomeScreen
                    }

                val rootNavHostController = rememberNavController()

                AcessIFTheme {
                    RootNavGraph(
                        startDestination = startDestination,
                        rootNavController = rootNavHostController,
                    )
                }
            }
        }
    }
}
