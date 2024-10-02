package com.mv.acessif.presentation.home.transcriptionDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.mv.acessif.presentation.home.newTranscription.NewTranscriptionScreen
import com.mv.acessif.presentation.home.summary.SummaryScreen
import com.mv.acessif.presentation.util.shareTextIntent
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.SupportBottomBar
import com.mv.acessif.ui.designSystem.components.TextContainer
import com.mv.acessif.ui.designSystem.components.button.util.BASE_FONT_SIZE
import com.mv.acessif.ui.designSystem.components.button.util.MAX_FONT_SIZE
import com.mv.acessif.ui.designSystem.components.button.util.MIN_FONT_SIZE
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.DarkSecondary
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.LightPrimary
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL
import kotlinx.serialization.Serializable

@Serializable
data class TranscriptionDetailScreen(
    val transcriptionId: Int,
    val originScreen: String,
)

fun NavGraphBuilder.transcriptionDetailScreen(
    modifier: Modifier,
    navController: NavHostController,
    rootNavController: NavHostController,
) {
    composable<TranscriptionDetailScreen> { entry ->
        val viewModel: TranscriptionDetailViewModel = hiltViewModel()

        val context = navController.context

        TranscriptionDetailScreen(
            modifier = modifier,
            originScreen = entry.toRoute<TranscriptionDetailScreen>().originScreen,
            state = viewModel.state.value,
            onIntent = { intent ->
                when (intent) {
                    TranscriptionDetailIntent.OnNavigateBack -> {
                        navController.navigateUp()
                    }

                    TranscriptionDetailIntent.OnNewTranscription -> {
                        navController.navigate(
                            NewTranscriptionScreen(
                                originScreen = entry.toRoute<TranscriptionDetailScreen>().originScreen,
                            ),
                        ) {
                            popUpTo(TranscriptionDetailScreen) {
                                inclusive = true
                            }
                        }
                    }

                    TranscriptionDetailIntent.OnShareTranscription -> {
                        val transcriptionPlainText = viewModel.state.value.transcription?.text
                        if (transcriptionPlainText != null) {
                            context.shareTextIntent(transcriptionPlainText)
                        }
                    }

                    is TranscriptionDetailIntent.OnSummarizeTranscription -> {
                        val transcriptionId = viewModel.state.value.transcription?.id
                        if (transcriptionId != null) {
                            navController.navigate(
                                SummaryScreen(
                                    transcriptionId = transcriptionId,
                                ),
                            )
                        }
                    }

                    TranscriptionDetailIntent.OnTryAgain -> {
                        viewModel.onTryAgainClicked()
                    }
                }
            },
        )
    }
}

@Composable
fun TranscriptionDetailScreen(
    modifier: Modifier = Modifier,
    originScreen: String,
    state: TranscriptionDetailScreenState,
    onIntent: (TranscriptionDetailIntent) -> Unit,
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
            origin = originScreen,
            supportIcon = {
                if (state.transcription?.segments.isNullOrEmpty().not()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = stringResource(R.string.share_transcription),
                        colorFilter = ColorFilter.tint(White),
                    )
                }
            },
            onBackPressed = { onIntent(TranscriptionDetailIntent.OnNavigateBack) },
            onSupportIconPressed = { onIntent(TranscriptionDetailIntent.OnShareTranscription) },
        )

        Spacer(modifier = Modifier.height(L))

        if (state.error != null) {
            ErrorComponent(
                modifier =
                    Modifier
                        .padding(horizontal = XL)
                        .fillMaxSize(),
                message = state.error.asString(),
                onTryAgain = { onIntent(TranscriptionDetailIntent.OnTryAgain) },
            )
        } else if (state.isLoading) {
            LoadingComponent(
                modifier =
                    Modifier
                        .padding(horizontal = XL)
                        .fillMaxSize(),
                label = stringResource(id = R.string.your_transcription_is_being_loaded),
            )
        } else if (state.transcription != null) {
            TranscriptionContent(
                transcription = state.transcription,
                onIntent = onIntent,
            )
        }
    }
}

@Composable
private fun TranscriptionContent(
    modifier: Modifier = Modifier,
    transcription: Transcription,
    onIntent: (TranscriptionDetailIntent) -> Unit,
) {
    Column(
        modifier = modifier,
    ) {
        var fontSize by remember { mutableIntStateOf(BASE_FONT_SIZE) }

        Box(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = L),
        ) {
            // TODO Audio player
            Surface(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                color = DarkSecondary,
            ) { Text(text = "Audio Player") }
        }

        Spacer(modifier = Modifier.height(L))

        TextContainer(
            modifier = Modifier.weight(1f),
        ) {
            LazyColumn(
                modifier =
                    Modifier
                        .padding(S),
            ) {
                items(transcription.segments) { segment ->
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style =
                                    SpanStyle(
                                        fontWeight = FontWeight.Bold,
                                        color = LightPrimary,
                                    ),
                            ) {
                                append("[${segment.start} - ${segment.end}] ")
                            }

                            append(segment.text)
                        },
                        fontSize = fontSize.sp,
                        lineHeight = (fontSize * 1.5).sp,
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(S))

        SupportBottomBar(
            summaryLabel = stringResource(id = if (transcription.summary == null) R.string.summarize else R.string.open_summary),
            modifier = Modifier.fillMaxWidth(),
            fontSize = fontSize,
            minFontSize = MIN_FONT_SIZE,
            maxFontSize = MAX_FONT_SIZE,
            onSizeChanged = { newSize ->
                fontSize = newSize
            },
            onSummaryPressed = {
                onIntent(
                    TranscriptionDetailIntent.OnSummarizeTranscription(
                        transcription.id,
                    ),
                )
            },
        )
    }
}

@Preview
@Composable
private fun TranscriptionDetailScreenPreview() {
    AcessIFTheme {
        TranscriptionDetailScreen(
            modifier = Modifier,
            originScreen = "Home Screen",
            state = fakeTranscriptionState(),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun TranscriptionDetailScreenStartPreview() {
    AcessIFTheme {
        TranscriptionDetailScreen(
            modifier = Modifier,
            originScreen = "Home Screen",
            state = TranscriptionDetailScreenState(),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun TranscriptionDetailScreenLoadingPreview() {
    AcessIFTheme {
        TranscriptionDetailScreen(
            modifier = Modifier,
            originScreen = "Home Screen",
            state =
                TranscriptionDetailScreenState(
                    isLoading = true,
                    error = null,
                ),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun TranscriptionDetailScreenErrorPreview() {
    AcessIFTheme {
        TranscriptionDetailScreen(
            modifier = Modifier,
            originScreen = "Home Screen",
            state =
                TranscriptionDetailScreenState(
                    isLoading = false,
                    error = UiText.StringResource(id = R.string.no_internet),
                ),
            onIntent = {},
        )
    }
}

private fun fakeTranscriptionState() =
    TranscriptionDetailScreenState(
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
                            start = 1.5F,
                            end = 2.0F,
                        ),
                        Segment(
                            id = 3,
                            text = "when an unknown printer took a galley of type and scrambled it to make a type specimen book.",
                            start = 2.0F,
                            end = 2.5F,
                        ),
                    ),
            ),
    )
