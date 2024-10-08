package com.mv.acessif.presentation.home.transcriptions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.domain.Language
import com.mv.acessif.domain.Segment
import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.UiText
import com.mv.acessif.presentation.home.newTranscription.NewTranscriptionScreen
import com.mv.acessif.presentation.home.transcriptionDetail.TranscriptionDetailScreen
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.ScreenHeader
import com.mv.acessif.ui.designSystem.components.button.MainActionButton
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.BodyMedium
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.XL
import kotlinx.serialization.Serializable
import java.time.Instant
import java.util.Date

@Serializable
object TranscriptionsScreen

fun NavGraphBuilder.transcriptionsScreen(
    modifier: Modifier,
    navController: NavHostController,
    rootNavController: NavHostController,
) {
    composable<TranscriptionsScreen> {
        val viewModel: TranscriptionsViewModel = hiltViewModel()

        val context = navController.context

        TranscriptionsScreen(
            modifier = modifier,
            state = viewModel.state.value,
            onIntent = {
                when (it) {
                    TranscriptionsIntent.OnNavigateBack -> {
                        navController.navigateUp()
                    }

                    TranscriptionsIntent.OnTryAgain -> {
                        viewModel.getTranscriptions()
                    }

                    TranscriptionsIntent.OnNewTranscription -> {
                        navController.navigate(NewTranscriptionScreen)
                    }

                    is TranscriptionsIntent.OnOpenTranscriptionDetail -> {
                        navController.navigate(
                            TranscriptionDetailScreen(
                                transcriptionId = it.transcriptionId,
                                originScreen = context.getString(R.string.my_transcriptions),
                            ),
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun TranscriptionsScreen(
    modifier: Modifier = Modifier,
    state: TranscriptionsScreenState,
    onIntent: (TranscriptionsIntent) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(bottom = XL),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ScreenHeader(
            origin = stringResource(id = R.string.home_screen),
            screenTitle = stringResource(id = R.string.my_transcriptions),
            onBackPressed = { onIntent(TranscriptionsIntent.OnNavigateBack) },
        )

        Spacer(modifier = Modifier.height(L))

        if (state.error != null) {
            ErrorComponent(
                modifier =
                    Modifier
                        .padding(horizontal = XL)
                        .fillMaxSize(),
                message = state.error.asString(),
                onTryAgain = { onIntent(TranscriptionsIntent.OnTryAgain) },
            )
        } else if (state.isLoading) {
            LoadingComponent(
                modifier =
                    Modifier
                        .padding(horizontal = XL)
                        .fillMaxSize(),
                label = stringResource(id = R.string.your_transcriptions_are_being_loaded),
            )
        } else if (state.transcriptions.isEmpty()) {
            TranscriptionsEmptyContent(
                modifier =
                    Modifier
                        .padding(horizontal = XL)
                        .fillMaxSize(),
                onIntent = onIntent,
            )
        } else {
            TranscriptionsContent(
                modifier = Modifier,
                transcriptions = state.transcriptions,
                onIntent = onIntent,
            )
        }
    }
}

@Composable
fun TranscriptionsEmptyContent(
    modifier: Modifier = Modifier,
    onIntent: (TranscriptionsIntent) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.padding(M),
            text = stringResource(R.string.you_dont_have_transcriptions),
            style = BodyMedium,
            color = Black,
        )

        MainActionButton(
            modifier =
                Modifier
                    .padding(horizontal = XL)
                    .fillMaxWidth(),
            label = stringResource(R.string.make_first_transcription),
        ) {
            onIntent(TranscriptionsIntent.OnNewTranscription)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TranscriptionsContent(
    modifier: Modifier = Modifier,
    transcriptions: Map<String, List<Transcription>>,
    onIntent: (TranscriptionsIntent) -> Unit,
) {
    LazyColumn(
        modifier = modifier,
    ) {
        transcriptions.forEach { (date, groupedTranscriptions) ->
            stickyHeader {
                Text(
                    modifier = Modifier.padding(M),
                    text = date,
                    style = BodyMedium,
                    color = Black,
                )
            }

            items(groupedTranscriptions) { transcription ->
                TranscriptionItem(
                    modifier = Modifier.padding(horizontal = S, vertical = 2.dp),
                    transcription = transcription,
                    onClick = {
                        onIntent(
                            TranscriptionsIntent.OnOpenTranscriptionDetail(
                                transcription.id,
                            ),
                        )
                    },
                )
            }
        }
    }
}

@Composable
@Preview
private fun TranscriptionScreenPreview() {
    AcessIFTheme {
        TranscriptionsScreen(
            modifier = Modifier,
            state =
                TranscriptionsScreenState(
                    isLoading = false,
                    error = null,
                    transcriptions = fakeTranscriptions(),
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun TranscriptionScreenEmptyPreview() {
    AcessIFTheme {
        TranscriptionsScreen(
            modifier = Modifier,
            state =
                TranscriptionsScreenState(
                    isLoading = false,
                    error = null,
                    transcriptions = emptyMap(),
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun TranscriptionScreenLoadingPreview() {
    AcessIFTheme {
        TranscriptionsScreen(
            modifier = Modifier,
            state =
                TranscriptionsScreenState(
                    isLoading = true,
                    error = null,
                    transcriptions = emptyMap(),
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun TranscriptionScreenErrorPreview() {
    AcessIFTheme {
        TranscriptionsScreen(
            modifier = Modifier,
            state =
                TranscriptionsScreenState(
                    isLoading = false,
                    error = UiText.StringResource(id = R.string.no_internet),
                    transcriptions = emptyMap(),
                ),
            onIntent = {},
        )
    }
}

private fun fakeTranscriptions() =
    mapOf(
        "10/10/2024" to
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
        "12/10/2024" to
            listOf(
                Transcription(
                    audioId = "audioId_12_10_24.mp3",
                    name = "audioId_12_10_24.mp3",
                    id = 1,
                    language = Language.PT,
                    createdAt =
                        Date.from(
                            Instant.parse("2021-10-12T10:10:10Z"),
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
                    audioId = "audioId_12_10_24.mp3",
                    name = "audioId_12_10_24.mp3",
                    id = 1,
                    language = Language.PT,
                    createdAt =
                        Date.from(
                            Instant.parse("2021-10-12T10:10:10Z"),
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
        "15/12/2024" to
            listOf(
                Transcription(
                    audioId = "audioId_15_12_24.mp3",
                    name = "audioId_15_12_24.mp3",
                    id = 1,
                    language = Language.PT,
                    createdAt =
                        Date.from(
                            Instant.parse("2021-12-15T10:10:10Z"),
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
    )
