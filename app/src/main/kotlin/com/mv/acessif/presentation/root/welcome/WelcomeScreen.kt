package com.mv.acessif.presentation.root.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.presentation.auth.login.LoginScreen
import com.mv.acessif.presentation.auth.signUp.SignUpScreen
import com.mv.acessif.presentation.root.RootStartDestination
import com.mv.acessif.presentation.root.demoTranscription.DemoTranscriptionScreen
import com.mv.acessif.ui.designSystem.components.button.MainActionButton
import com.mv.acessif.ui.designSystem.components.button.SecondaryActionButton
import com.mv.acessif.ui.designSystem.components.button.TertiaryActionButton
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.IconBigSize
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.LightNeutralBackground
import com.mv.acessif.ui.theme.LightPrimary
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleLarge
import com.mv.acessif.ui.theme.XL
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
                .background(color = LightNeutralBackground)
                .padding(vertical = XL),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = L),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.welcome_to),
                style = TitleLarge,
            )

            Spacer(modifier = Modifier.width(S))

            Text(
                text = stringResource(id = R.string.app_name),
                style = TitleLarge.copy(fontWeight = FontWeight.Black),
                color = LightPrimary,
            )
        }

        Column(
            modifier = Modifier.fillMaxSize().weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.size(IconBigSize),
                painter = painterResource(id = R.drawable.ic_speech_to_text),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = LightPrimary),
            )

            Spacer(modifier = Modifier.height(S))

            MainActionButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = XL),
                label = stringResource(id = R.string.start_a_transcription),
            ) {
                onIntent(WelcomeScreenIntent.OnTranscriptPressed)
            }
        }

        Column(
            modifier = Modifier.fillMaxSize().weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TertiaryActionButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = XXXL),
                label = stringResource(id = R.string.sign_in),
            ) {
                onIntent(WelcomeScreenIntent.OnLoginPressed)
            }

            Spacer(modifier = Modifier.height(L))

            SecondaryActionButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = XXXL),
                label = stringResource(id = R.string.sign_up),
            ) {
                onIntent(WelcomeScreenIntent.OnSignupPressed)
            }
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
