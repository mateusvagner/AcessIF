package com.mv.acessif.presentation.home.newTranscription

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mv.acessif.R
import com.mv.acessif.domain.Language
import com.mv.acessif.domain.Segment
import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.UiText
import com.mv.acessif.presentation.home.summary.SummaryScreen
import com.mv.acessif.presentation.home.transcriptionDetail.TranscriptionDetailScreen
import com.mv.acessif.presentation.util.shareTextIntent
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.button.MainActionButton
import com.mv.acessif.ui.designSystem.components.button.SecondaryActionButton
import com.mv.acessif.ui.designSystem.components.button.TertiaryActionButton
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.LightBackground
import com.mv.acessif.ui.theme.TitleMedium
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL
import com.mv.acessif.ui.theme.XXL
import com.mv.acessif.ui.theme.XXXL
import kotlinx.serialization.Serializable

@Deprecated("Not used anymore")
@Serializable
data class NewTranscriptionScreen(
    val originScreen: String,
)

@Deprecated("Not used anymore")
fun NavGraphBuilder.newTranscriptionScreen(
    modifier: Modifier,
    navController: NavHostController,
    rootNavController: NavHostController,
) {
    composable<NewTranscriptionScreen> { entry ->
        val viewModel: NewTranscriptionViewModel = hiltViewModel()

        val context = navController.context

        val filePickerLauncher =
            rememberLauncherForActivityResult(
                contract = ActivityResultContracts.OpenDocument(),
            ) { uri: Uri? ->
                if (uri != null) {
                    viewModel.handleFileUri(
                        uri,
                        context = context,
                    )
                } else {
                    viewModel.handleFileUriError()
                }
            }

        NewTranscriptionScreen(
            modifier = modifier,
            originScreen = entry.toRoute<NewTranscriptionScreen>().originScreen,
            state = viewModel.state.value,
            onIntent = { intent ->
                when (intent) {
                    NewTranscriptionIntent.OnAttachFile -> {
                        filePickerLauncher.launch(arrayOf("audio/*"))
                    }

                    NewTranscriptionIntent.OnNavigateBack -> {
                        navController.navigateUp()
                    }

                    is NewTranscriptionIntent.OnOpenTranscription -> {
                        navController.navigate(
                            TranscriptionDetailScreen(
                                transcriptionId = intent.transcriptionId,
                                originScreen = context.getString(R.string.new_transcription_screen),
                            ),
                        )
                    }

                    is NewTranscriptionIntent.OnSummarizeTranscription -> {
                        val transcriptionId = viewModel.state.value.transcription?.id
                        if (transcriptionId != null) {
                            navController.navigate(
                                SummaryScreen(
                                    transcriptionId = transcriptionId,
                                ),
                            )
                        }
                    }

                    NewTranscriptionIntent.OnShareTranscription -> {
                        val transcriptionPlainText = viewModel.state.value.transcription?.text
                        if (transcriptionPlainText != null) {
                            context.shareTextIntent(transcriptionPlainText)
                        }
                    }

                    NewTranscriptionIntent.OnNewTranscription -> {
                        viewModel.onNewTranscription()
                    }
                }
            },
        )
    }
}

@Composable
fun NewTranscriptionScreen(
    modifier: Modifier = Modifier,
    originScreen: String,
    state: NewTranscriptionScreenState,
    onIntent: (NewTranscriptionIntent) -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = LightBackground)
                .padding(bottom = XL),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DefaultScreenHeader(
            origin = originScreen,
            onBackPressed = { onIntent(NewTranscriptionIntent.OnNavigateBack) },
        )

        Spacer(modifier = Modifier.height(L))

        if (state.transcription == null) {
            MainActionButton(
                modifier =
                    Modifier
                        .padding(horizontal = XL)
                        .fillMaxWidth(),
                label = stringResource(R.string.attach_audio_file),
                isEnabled = !state.isLoading,
                leadingImage = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_upload_file),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = White),
                    )
                },
            ) {
                onIntent(NewTranscriptionIntent.OnAttachFile)
            }

            Spacer(modifier = Modifier.height(L))
        }

        TranscriptionContent(
            state = state,
            onIntent = onIntent,
        )
    }
}

@Composable
private fun TranscriptionContent(
    modifier: Modifier = Modifier,
    onIntent: (NewTranscriptionIntent) -> Unit,
    state: NewTranscriptionScreenState,
) {
    if (state.error != null) {
        ErrorComponent(
            modifier =
                Modifier
                    .padding(horizontal = XL)
                    .fillMaxSize(),
            message = state.error.asString(),
        )
    } else if (state.isLoading) {
        LoadingComponent(
            modifier =
                Modifier
                    .padding(horizontal = XL)
                    .fillMaxSize(),
            label = stringResource(id = R.string.your_audio_is_been_processed),
        )
    } else if (state.transcription != null) {
        MainContent(
            modifier = modifier,
            state = state,
            onIntent = onIntent,
        )
    }
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    state: NewTranscriptionScreenState,
    onIntent: (NewTranscriptionIntent) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(id = R.string.your_transcription_is_ready), style = TitleMedium)

        Spacer(modifier = Modifier.height(XXL))

        MainActionButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = XL),
            label = stringResource(id = R.string.open_transcription),
        ) {
            onIntent(NewTranscriptionIntent.OnOpenTranscription(state.transcription!!.id))
        }

        Spacer(modifier = Modifier.height(XXL))

        SecondaryActionButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = XXXL),
            label = stringResource(id = R.string.summarize),
        ) {
            onIntent(NewTranscriptionIntent.OnSummarizeTranscription(state.transcription!!.id))
        }

        Spacer(modifier = Modifier.height(L))

        SecondaryActionButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = XXXL),
            label = stringResource(id = R.string.share_transcription),
        ) {
            onIntent(NewTranscriptionIntent.OnShareTranscription)
        }

        Spacer(modifier = Modifier.height(XXL))

        TertiaryActionButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = XXXL),
            label = stringResource(id = R.string.make_other_transcription),
        ) {
            onIntent(NewTranscriptionIntent.OnNewTranscription)
        }
    }
}

@Preview
@Composable
private fun NewTranscriptionScreenPreview() {
    AcessIFTheme {
        NewTranscriptionScreen(
            modifier = Modifier,
            originScreen = "Home Screen",
            state = NewTranscriptionScreenState(),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun NewTranscriptionScreenTranscribedPreview() {
    AcessIFTheme {
        NewTranscriptionScreen(
            modifier = Modifier,
            originScreen = "Home Screen",
            state = fakeTranscriptionState(),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun NewTranscriptionScreenLoadingPreview() {
    AcessIFTheme {
        NewTranscriptionScreen(
            modifier = Modifier,
            originScreen = "Home Screen",
            state =
                NewTranscriptionScreenState(
                    isLoading = true,
                    error = null,
                ),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun NewTranscriptionScreenErrorPreview() {
    AcessIFTheme {
        NewTranscriptionScreen(
            modifier = Modifier,
            originScreen = "Home Screen",
            state =
                NewTranscriptionScreenState(
                    isLoading = false,
                    error = UiText.StringResource(id = R.string.no_internet),
                ),
            onIntent = {},
        )
    }
}

private fun fakeTranscriptionState() =
    NewTranscriptionScreenState(
        transcription =
            Transcription(
                audioId = "audioId.mp3",
                name = "Transcription Name",
                id = 1,
                language = Language.PT,
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
    )
