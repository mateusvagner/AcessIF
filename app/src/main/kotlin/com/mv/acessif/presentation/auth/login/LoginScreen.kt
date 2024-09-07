package com.mv.acessif.presentation.auth.login

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
import com.mv.acessif.presentation.home.home.HomeNavGraph
import kotlinx.serialization.Serializable

@Serializable
object LoginScreen

fun NavGraphBuilder.loginScreen(
    modifier: Modifier,
    rootNavController: NavHostController,
) {
    composable<LoginScreen> {
        LoginScreen(modifier = modifier) {
            rootNavController.navigate(HomeNavGraph) {
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
fun LoginScreen(
    modifier: Modifier,
    onNavigateToHome: () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(Color.Green),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onNavigateToHome() }) {
            Text(text = "Go to Home")
        }
    }
}

@Composable
@Preview
private fun LoginScreenPreview(modifier: Modifier) {
    LoginScreen(modifier = modifier, onNavigateToHome = {})
}
