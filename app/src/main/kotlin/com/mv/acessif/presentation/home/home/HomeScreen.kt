package com.mv.acessif.presentation.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.presentation.root.welcome.WelcomeScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeScreen : HomeGraphTab

fun NavGraphBuilder.homeScreen(
    modifier: Modifier,
    navController: NavHostController,
    rootNavController: NavHostController,
) {
    composable<HomeScreen> {
        val viewModel: HomeViewModel = hiltViewModel()
        val userName by viewModel.name.collectAsState()

        HomeScreen(modifier = modifier, userName = userName) {
            rootNavController.navigate(WelcomeScreen) {
                rootNavController.currentBackStackEntry?.destination?.route?.let { screenRoute ->
                    popUpTo(screenRoute) {
                        inclusive = true
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userName: String,
    onNavigateToWelcome: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Welcome, $userName!",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavigateToWelcome() }) {
            Text(text = "Go to Welcome")
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    HomeScreen(
        modifier = Modifier,
        userName = "Mateus",
        onNavigateToWelcome = {},
    )
}
