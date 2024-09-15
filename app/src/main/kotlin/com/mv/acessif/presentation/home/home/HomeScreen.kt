package com.mv.acessif.presentation.home.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.presentation.home.newTranscription.NewTranscriptionScreen
import com.mv.acessif.presentation.home.transcriptions.TranscriptionsScreen
import com.mv.acessif.presentation.root.welcome.WelcomeScreen
import com.mv.acessif.ui.designSystem.components.ScreenHeader
import com.mv.acessif.ui.designSystem.components.button.MainActionButton
import com.mv.acessif.ui.designSystem.components.button.SecondaryActionButton
import com.mv.acessif.ui.designSystem.components.button.TextButtonComponent
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.IconBigSize
import com.mv.acessif.ui.theme.LightPrimary
import com.mv.acessif.ui.theme.NeutralBackground
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.XL
import com.mv.acessif.ui.theme.XXXL
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

        val context = navController.context

        LaunchedEffect(key1 = Unit) {
            viewModel.onLogoutSuccess.collect {
                rootNavController.navigate(WelcomeScreen) {
                    rootNavController.currentBackStackEntry?.destination?.route?.let { screenRoute ->
                        popUpTo(screenRoute) {
                            inclusive = true
                        }
                    }
                }
            }
        }
        HomeScreen(
            modifier = modifier,
            onIntent = { intent ->
                when (intent) {
                    HomeIntent.OnNewTranscription -> {
                        navController.navigate(
                            NewTranscriptionScreen(
                                originScreen = context.getString(R.string.home_screen),
                            ),
                        )
                    }

                    HomeIntent.OnMyTranscriptions -> {
                        navController.navigate(TranscriptionsScreen)
                    }

                    HomeIntent.OnLogout -> {
                        viewModel.logoutUser()
                    }
                }
            },
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onIntent: (HomeIntent) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = NeutralBackground)
                .padding(bottom = XL),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ScreenHeader(
            modifier = Modifier.padding(top = XXXL),
            screenTitle = stringResource(id = R.string.home_screen),
        )

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .weight(1f),
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
                label = stringResource(id = R.string.start_a_new_transcription),
            ) {
                onIntent(HomeIntent.OnNewTranscription)
            }

            Spacer(modifier = Modifier.height(XL))

            SecondaryActionButton(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = XL),
                label = stringResource(id = R.string.check_my_transcriptions),
            ) {
                onIntent(HomeIntent.OnMyTranscriptions)
            }
        }

        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(end = S)
                    .weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End,
        ) {
            TextButtonComponent(
                leadingImage = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout),
                        colorFilter = ColorFilter.tint(color = Color.Black),
                        contentDescription = null,
                    )
                },
                label = stringResource(R.string.logout),
                semantics = stringResource(R.string.logout),
            ) {
                onIntent(HomeIntent.OnLogout)
            }
        }
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    AcessIFTheme {
        HomeScreen(
            modifier = Modifier,
            onIntent = {},
        )
    }
}
