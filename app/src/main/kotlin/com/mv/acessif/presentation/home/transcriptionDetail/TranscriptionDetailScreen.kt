package com.mv.acessif.presentation.home.transcriptionDetail

import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.PlayerControlView
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mv.acessif.R
import com.mv.acessif.domain.Language
import com.mv.acessif.domain.Segment
import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.UiText
import com.mv.acessif.presentation.home.components.SupportBottomBar
import com.mv.acessif.presentation.home.components.TranscriptionContainer
import com.mv.acessif.presentation.home.newTranscription.NewTranscriptionScreen
import com.mv.acessif.presentation.home.summary.SummaryScreen
import com.mv.acessif.presentation.util.formatTo
import com.mv.acessif.presentation.util.shareTextIntent
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.button.util.BASE_FONT_SIZE
import com.mv.acessif.ui.designSystem.components.button.util.MAX_FONT_SIZE
import com.mv.acessif.ui.designSystem.components.button.util.MIN_FONT_SIZE
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BaseCornerRadius
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class TranscriptionDetailScreen(
    val transcriptionId: Int,
    val originScreen: String,
)

@OptIn(UnstableApi::class)
fun NavGraphBuilder.transcriptionDetailScreen(
    modifier: Modifier,
    navController: NavHostController,
    rootNavController: NavHostController,
) {
    composable<TranscriptionDetailScreen> { entry ->
        val viewModel: TranscriptionDetailViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

        val context = navController.context

        var lifecycle by remember {
            mutableStateOf(Lifecycle.Event.ON_CREATE)
        }

        val lifecycleOwner = LocalLifecycleOwner.current

        DisposableEffect(lifecycleOwner) {
            val observer =
                LifecycleEventObserver { _, event ->
                    lifecycle = event
                }

            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }

        TranscriptionDetailScreen(
            modifier = modifier,
            originScreen = entry.toRoute<TranscriptionDetailScreen>().originScreen,
            player = {
                AndroidView(
                    factory = { context ->
                        PlayerControlView(context).also {
                            it.player = viewModel.player
                            it.showTimeoutMs = 0
                        }
                    },
                    update = { playerView ->
                        when (lifecycle) {
                            Lifecycle.Event.ON_PAUSE -> {
                                playerView.player?.pause()
                            }

                            Lifecycle.Event.ON_RESUME -> {
                                playerView.player?.play()
                            }

                            else -> Unit
                        }
                    },
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(BaseCornerRadius)),
                )
            },
            state = state,
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
                        val transcriptionPlainText = state.transcription?.text
                        if (transcriptionPlainText != null) {
                            context.shareTextIntent(transcriptionPlainText)
                        }
                    }

                    is TranscriptionDetailIntent.OnSummarizeTranscription -> {
                        val transcriptionId = state.transcription?.id
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

                    is TranscriptionDetailIntent.OnTranscriptionNameEdited -> {
                        viewModel.onNewTranscriptionName(intent.newName)
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
    player: @Composable () -> Unit,
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
                player = player,
                playerCurrentPosition = state.currentPosition,
                onIntent = onIntent,
            )
        }
    }
}

@Composable
private fun TranscriptionContent(
    modifier: Modifier = Modifier,
    transcription: Transcription,
    player: @Composable () -> Unit,
    playerCurrentPosition: Float,
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
            player()
        }

        Spacer(modifier = Modifier.height(L))

        TranscriptionContainer(
            modifier = Modifier.weight(1f),
            name = transcription.name,
            onEditTranscriptionName = {
                onIntent(TranscriptionDetailIntent.OnTranscriptionNameEdited(it))
            },
            createdAt = transcription.createdAt?.formatTo("dd/MM/yyyy"),
        ) {
            val listState = rememberLazyListState()

            LazyColumn(
                state = listState,
                contentPadding = PaddingValues(bottom = L, start = L, end = L),
                verticalArrangement = Arrangement.spacedBy(M),
            ) {
                itemsIndexed(transcription.segments) { index, segment ->
                    val isHighlight = playerCurrentPosition in segment.start..segment.end

                    Text(
                        text = segment.text,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = fontSize.sp,
                        lineHeight = (fontSize * 1.5).sp,
                        fontWeight = if (isHighlight) FontWeight.Black else FontWeight.Normal,
                    )

                    if (isHighlight) {
                        LaunchedEffect(key1 = index) {
                            listState.animateScrollToItem(index)
                        }
                    }
                }
            }
        }

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
            player = {},
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
            player = {},
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
            player = {},
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
            player = {},
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
                createdAt = Date(),
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
