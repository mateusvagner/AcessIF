package com.mv.acessif.presentation.root.welcome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.presentation.auth.login.LoginScreen
import com.mv.acessif.presentation.auth.signUp.SignUpScreen
import com.mv.acessif.presentation.components.SignInSignUpActionCard
import com.mv.acessif.presentation.components.TranscribeActionCard
import com.mv.acessif.presentation.root.RootStartDestination
import com.mv.acessif.presentation.root.demoTranscription.DemoTranscriptionScreen
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.XXXL
import kotlinx.serialization.Serializable

@Serializable
object WelcomeScreen : RootStartDestination

fun NavGraphBuilder.welcomeScreen(
    modifier: Modifier,
    rootNavController: NavHostController,
) {
    composable<WelcomeScreen> {
        val viewModel: WelcomeScreenViewModel = hiltViewModel()

        WelcomeScreen(
            modifier = modifier,
            onIntent = { intent ->
                when (intent) {
                    WelcomeScreenIntent.OnTranscriptPressed -> {
                        rootNavController.navigate(DemoTranscriptionScreen)
                    }

                    WelcomeScreenIntent.OnLoginPressed -> {
                        rootNavController.navigate(LoginScreen)
                    }

                    WelcomeScreenIntent.OnSignupPressed -> {
                        rootNavController.navigate(SignUpScreen)
                    }
                }
            },
        )
    }
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onIntent: (WelcomeScreenIntent) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DefaultScreenHeader(
            modifier =
                Modifier
                    .background(color = MaterialTheme.colorScheme.primary),
            screenTitle = stringResource(R.string.welcome),
        )

        Spacer(modifier = Modifier.height(L))

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(XXXL),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TranscribeActionCard(
                modifier = Modifier.padding(horizontal = L),
                onCardClick = { onIntent(WelcomeScreenIntent.OnTranscriptPressed) },
            )

            SignInSignUpActionCard(
                modifier = Modifier.padding(horizontal = L),
                onSignInPressed = { onIntent(WelcomeScreenIntent.OnLoginPressed) },
                onSignupPressed = { onIntent(WelcomeScreenIntent.OnSignupPressed) },
            )
        }
    }
}

@Composable
@Preview
private fun WelcomeScreenPreview() {
    AcessIFTheme {
        WelcomeScreen(
            modifier = Modifier,
            onIntent = {},
        )
    }
}
