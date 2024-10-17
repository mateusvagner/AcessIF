package com.mv.acessif

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.mv.acessif.presentation.navigation.Navigator
import com.mv.acessif.presentation.navigation.observeNavigationEvents
import com.mv.acessif.presentation.root.RootNavGraph
import com.mv.acessif.ui.theme.AcessIFTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var navigator: Navigator

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()
            navController.observeNavigationEvents(navigator)

            AcessIFTheme {
                RootNavGraph(
                    startDestination = navigator.startDestination,
                    navController = navController,
                )
            }
        }
    }
}
