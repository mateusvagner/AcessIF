package com.mv.acessif.presentation.home.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.domain.Language
import com.mv.acessif.domain.Segment
import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.UiText
import com.mv.acessif.presentation.components.TranscribeActionCard
import com.mv.acessif.presentation.home.home.components.RightSideMenu
import com.mv.acessif.presentation.home.home.components.SeeAllButton
import com.mv.acessif.presentation.home.home.components.TranscriptionCard
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleMedium
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL
import java.time.Instant
import java.util.Date

fun NavGraphBuilder.homeRoute(
    modifier: Modifier,
    navController: NavHostController,
) {
    composable<HomeGraph.HomeRoute> {
        val viewModel: HomeViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val context = navController.context

        val filePickerLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.OpenDocument(),
            ) { uri: Uri? ->
                if (uri != null) {
                    viewModel.handleFileUri(uri)
                } else {
                    viewModel.handleFileUriError()
                }
            }

        HomeScreen(
            modifier = modifier,
            state = state,
            onIntent = { intent ->
                when (intent) {
                    HomeIntent.OnNewTranscription -> {
                        filePickerLauncher.launch(arrayOf("audio/*"))
                    }
                    else -> viewModel.handleIntent(intent)
                }
            },
        )
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeScreenState,
    onIntent: (HomeIntent) -> Unit,
) {
    var isContextMenuVisible by rememberSaveable { mutableStateOf(false) }

    Box {
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
                screenTitle =
                    if (state.userName.isEmpty()) {
                        stringResource(R.string.welcome)
                    } else {
                        stringResource(R.string.welcome_user, state.userName)
                    },
                supportIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_menu),
                        colorFilter = ColorFilter.tint(color = White),
                        contentDescription = stringResource(R.string.menu),
                    )
                },
                onSupportIconPressed = {
                    isContextMenuVisible = true
                },
            )

            Box {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                ) {
                    Spacer(modifier = Modifier.height(L))

                    TranscriptionsSection(
                        state = state.transcriptionsSectionState,
                        onIntent = onIntent,
                    )

                    Spacer(modifier = Modifier.height(XL))

                    TranscribeActionCard(
                        modifier = Modifier.padding(horizontal = L),
                        onCardClick = { onIntent(HomeIntent.OnNewTranscription) },
                    )

                    Spacer(modifier = Modifier.height(L))
                }

                if (state.isLoadingTranscription) {
                    LoadingComponent(
                        modifier =
                            Modifier
                                .fillMaxSize()
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = { },
                                ),
                        backgroundAlpha = 0.95F,
                        label = stringResource(R.string.your_transcription_is_being_loaded),
                    )
                } else if (state.errorTranscription != null) {
                    Surface {
                        ErrorComponent(
                            modifier =
                                Modifier
                                    .fillMaxSize()
                                    .background(color = MaterialTheme.colorScheme.background.copy(alpha = 0.95F)),
                            message = state.errorTranscription.asString(),
                            onTryAgain = { onIntent(HomeIntent.OnReloadScreen) },
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = isContextMenuVisible,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6F),
            ) {
            }
        }

        AnimatedVisibility(
            visible = isContextMenuVisible,
            enter = slideInHorizontally(animationSpec = tween(durationMillis = 300)) { fullScreen -> fullScreen },
            exit = slideOutHorizontally { fullScreen -> fullScreen },
        ) {
            RightSideMenu(
                modifier = modifier,
                onClose = { isContextMenuVisible = false },
                onAboutProject = {
                    isContextMenuVisible = false
                    onIntent(HomeIntent.OnAboutTheProject)
                },
                onContactUs = {
                    isContextMenuVisible = false
                    onIntent(HomeIntent.OnContactUs)
                },
                onLogout = {
                    isContextMenuVisible = false
                    onIntent(HomeIntent.OnLogout)
                },
            )
        }
    }
}

@Composable
private fun TranscriptionsSection(
    state: TranscriptionsSectionState,
    onIntent: (HomeIntent) -> Unit,
) {
    if (state.isLoading) {
        LoadingComponent(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = XL),
            label = stringResource(R.string.your_last_transcription_is_being_loaded),
        )
    } else if (state.error != null) {
        ErrorComponent(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(vertical = XL),
            message = state.error.asString(),
        )
    } else if (state.transcriptions.isEmpty().not()) {
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
                    style =
                        TitleMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Black,
                        ),
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
                items(state.transcriptions) { transcription ->
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
}

@Composable
@Preview
private fun HomeScreenPreview() {
    AcessIFTheme {
        HomeScreen(
            modifier = Modifier,
            state =
                HomeScreenState(
                    userName = "Mateus",
                    transcriptionsSectionState =
                        TranscriptionsSectionState(
                            isLoading = false,
                            error = null,
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
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
private fun HomeScreenDarkPreview() {
    AcessIFTheme {
        HomeScreen(
            modifier = Modifier,
            state =
                HomeScreenState(
                    transcriptionsSectionState =
                        TranscriptionsSectionState(
                            isLoading = false,
                            error = null,
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
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun HomeScreenLoadingPreview() {
    AcessIFTheme {
        HomeScreen(
            modifier = Modifier,
            state =
                HomeScreenState(
                    isLoadingTranscription = true,
                    transcriptionsSectionState =
                        TranscriptionsSectionState(
                            isLoading = false,
                            error = null,
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
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun HomeScreenLoadingTranscriptionsPreview() {
    AcessIFTheme {
        HomeScreen(
            modifier = Modifier,
            state =
                HomeScreenState(
                    transcriptionsSectionState =
                        TranscriptionsSectionState(
                            isLoading = true,
                            error = null,
                            transcriptions = emptyList(),
                        ),
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun HomeScreenErrorTranscriptionsPreview() {
    AcessIFTheme {
        HomeScreen(
            modifier = Modifier,
            state =
                HomeScreenState(
                    transcriptionsSectionState =
                        TranscriptionsSectionState(
                            isLoading = false,
                            error = UiText.StringResource(id = R.string.no_internet),
                            transcriptions = emptyList(),
                        ),
                ),
            onIntent = {},
        )
    }
}
