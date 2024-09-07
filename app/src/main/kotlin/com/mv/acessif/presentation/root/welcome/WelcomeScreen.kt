package com.mv.acessif.presentation.root.welcome

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.presentation.auth.login.LoginScreen
import com.mv.acessif.presentation.auth.signUp.SignUpScreen
import com.mv.acessif.presentation.home.home.HomeNavGraph
import com.mv.acessif.presentation.root.demoTranscription.DemoTranscriptionScreen
import kotlinx.serialization.Serializable

@Serializable
object WelcomeScreen

fun NavGraphBuilder.welcomeScreen(
    modifier: Modifier,
    rootNavController: NavHostController,
) {
    composable<WelcomeScreen> {
        WelcomeScreen(
            modifier = modifier,
            onNavigateToDemoTranscription = {
                rootNavController.navigate(DemoTranscriptionScreen)
            },
            onNavigateToHome = {
                rootNavController.navigate(HomeNavGraph) {
                    rootNavController.currentBackStackEntry?.destination?.route?.let { screenRoute ->
                        popUpTo(screenRoute) {
                            inclusive = true
                        }
                    }
                }
            },
            onNavigateToLogin = {
                rootNavController.navigate(LoginScreen)
            },
            onNavigateToSignUp = {
                rootNavController.navigate(SignUpScreen)
            },
        )
    }
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToDemoTranscription: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToSignUp: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(Color.LightGray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Welcome to AcessIF",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavigateToDemoTranscription() }) {
            Text(text = "Demo Transcription")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavigateToHome() }) {
            Text(text = "Go to Home")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavigateToLogin() }) {
            Text(text = "Go to Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavigateToSignUp() }) {
            Text(text = "Go to Sign Up")
        }
    }
}

@Composable
@Preview
private fun WelcomeScreenPreview() {
    WelcomeScreen(
        modifier = Modifier,
        onNavigateToDemoTranscription = {},
        onNavigateToHome = {},
        onNavigateToLogin = {},
        onNavigateToSignUp = {},
    )
}
