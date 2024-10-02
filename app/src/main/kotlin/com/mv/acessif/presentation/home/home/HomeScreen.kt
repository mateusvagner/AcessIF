package com.mv.acessif.presentation.home.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.mv.acessif.domain.Language
import com.mv.acessif.domain.Segment
import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.home.home.components.SeeAllButton
import com.mv.acessif.presentation.home.home.components.TranscribeActionCard
import com.mv.acessif.presentation.home.home.components.TranscriptionCard
import com.mv.acessif.presentation.home.newTranscription.NewTranscriptionScreen
import com.mv.acessif.presentation.home.transcriptionDetail.TranscriptionDetailScreen
import com.mv.acessif.presentation.home.transcriptions.TranscriptionsScreen
import com.mv.acessif.presentation.root.welcome.WelcomeScreen
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.button.TextButtonComponent
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.DarkGrey
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.LightNeutralBackground
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleMedium
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.Date

@Serializable
object HomeScreen

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
            userName = "", // TODO
            state = viewModel.state.value,
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
                        navController.navigate(
                            TranscriptionsScreen,
                        )
                    }

                    HomeIntent.OnLogout -> {
                        viewModel.logoutUser()
                    }

                    is HomeIntent.OnTranscriptionPressed -> {
                        navController.navigate(
                            TranscriptionDetailScreen(
                                transcriptionId = intent.transcription.id,
                                originScreen = context.getString(R.string.home_screen),
                            ),
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    userName: String,
    state: HomeScreenState,
    onIntent: (HomeIntent) -> Unit,
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
            screenTitle = stringResource(R.string.welcome_user, userName),
            supportIcon = {
                Image(
                    painter = painterResource(id = R.drawable.ic_menu),
                    colorFilter = ColorFilter.tint(color = White),
                    contentDescription = stringResource(R.string.menu),
                )
            },
            onSupportIconPressed = {
                // TODO add about
            },
        )

        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
        ) {
            Spacer(modifier = Modifier.height(L))

            if (state.transcriptions.isNotEmpty()) {
                TranscriptionsSection(
                    transcriptions = state.transcriptions,
                    onIntent = onIntent,
                )
            }

            Spacer(modifier = Modifier.height(XL))

            TranscribeActionCard(
                modifier = Modifier.padding(horizontal = L),
                onCardClick = { onIntent(HomeIntent.OnNewTranscription) },
            )

            Spacer(modifier = Modifier.height(L))

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(end = S),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End,
            ) {
                TextButtonComponent(
                    trailingImage = {
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
}

@Composable
private fun TranscriptionsSection(
    transcriptions: List<Transcription>,
    onIntent: (HomeIntent) -> Unit,
) {
    Column {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = L)
                    .padding(end = M),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = stringResource(R.string.my_transcriptions),
                style = TitleMedium.copy(color = MaterialTheme.colorScheme.onBackground),
            )

            SeeAllButton {
                onIntent(HomeIntent.OnMyTranscriptions)
            }
        }

        Spacer(modifier = Modifier.height(S))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(M),
            contentPadding = PaddingValues(horizontal = L),
        ) {
            items(transcriptions) { transcription ->
                TranscriptionCard(
                    transcription = transcription,
                    onTranscriptionClick = {
                        onIntent(HomeIntent.OnTranscriptionPressed(transcription))
                    },
                )
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
            userName = "",
            state =
                HomeScreenState(
                    transcriptions =
                        listOf(
                            Transcription(
                                audioId = "audioId_10_10_24.mp3",
                                name = "Podcast Transcription",
                                id = 1,
                                language = Language.PT,
                                createdAt =
                                    Date.from(
                                        Instant.parse("2021-10-10T10:10:10Z"),
                                    ),
                                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                                segments =
                                    listOf(
                                        Segment(
                                            id = 1,
                                            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                                            start = 0.0F,
                                            end = 1.5F,
                                        ),
                                        Segment(
                                            id = 2,
                                            text = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, ",
                                            start = 1.51F,
                                            end = 2.0F,
                                        ),
                                        Segment(
                                            id = 3,
                                            text = "when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                                            start = 2.1F,
                                            end = 2.5F,
                                        ),
                                    ),
                            ),
                            Transcription(
                                audioId = "audioId_10_10_24.mp3",
                                name = "Lecture Transcription",
                                id = 1,
                                language = Language.PT,
                                createdAt =
                                    Date.from(
                                        Instant.parse("2021-10-10T10:10:10Z"),
                                    ),
                                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                                segments =
                                    listOf(
                                        Segment(
                                            id = 1,
                                            text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry.",
                                            start = 0.0F,
                                            end = 1.5F,
                                        ),
                                        Segment(
                                            id = 2,
                                            text = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, ",
                                            start = 1.51F,
                                            end = 2.0F,
                                        ),
                                        Segment(
                                            id = 3,
                                            text = "when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                                            start = 2.1F,
                                            end = 2.5F,
                                        ),
                                    ),
                            ),
                        ),
                ),
            onIntent = {},
        )
    }
}
