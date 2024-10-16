package com.mv.acessif.presentation.root.demoTranscription

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.presentation.UiText
import com.mv.acessif.presentation.home.components.SupportBottomBar
import com.mv.acessif.presentation.home.components.TranscriptionContainer
import com.mv.acessif.presentation.root.RootGraph
import com.mv.acessif.presentation.util.shareTextIntent
import com.mv.acessif.ui.designSystem.components.CustomButton
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.button.util.BASE_FONT_SIZE
import com.mv.acessif.ui.designSystem.components.button.util.MAX_FONT_SIZE
import com.mv.acessif.ui.designSystem.components.button.util.MIN_FONT_SIZE
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleMedium
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL

fun NavGraphBuilder.demoTranscriptionRoute(
    modifier: Modifier,
    navController: NavHostController,
) {
    composable<RootGraph.DemoTranscriptionRoute> {
        val viewModel: DemoTranscriptionViewModel = hiltViewModel()

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

        DemoTranscriptionScreen(
            modifier = modifier,
            state = viewModel.state.value,
            onIntent = { intent ->
                when (intent) {
                    DemoTranscriptionIntent.OnAttachFile -> {
                        filePickerLauncher.launch(arrayOf("audio/*"))
                    }

                    DemoTranscriptionIntent.OnNavigateBack -> {
                        navController.navigateUp()
                    }

                    DemoTranscriptionIntent.OnShareTranscription -> {
                        context.shareTextIntent(viewModel.state.value.transcription)
                    }
                }
            },
        )
    }
}

@Composable
fun DemoTranscriptionScreen(
    modifier: Modifier = Modifier,
    state: DemoTranscriptionScreenState,
    onIntent: (DemoTranscriptionIntent) -> Unit,
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
            origin = stringResource(id = R.string.home_screen),
            supportIcon = {
                if (state.transcription.isNotBlank()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = stringResource(R.string.share_transcription),
                        colorFilter = ColorFilter.tint(White),
                    )
                }
            },
            onBackPressed = { onIntent(DemoTranscriptionIntent.OnNavigateBack) },
            onSupportIconPressed = { onIntent(DemoTranscriptionIntent.OnShareTranscription) },
        )

        Spacer(modifier = Modifier.height(L))

        CustomButton(
            modifier = Modifier.fillMaxWidth().padding(horizontal = XL),
            isLightColor = isSystemInDarkTheme(),
            color = MaterialTheme.colorScheme.primary,
            onClick = { onIntent(DemoTranscriptionIntent.OnAttachFile) },
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_upload_file),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
            )

            Spacer(modifier = Modifier.width(S))
            Text(
                text =
                    if (state.transcription.isEmpty()) {
                        stringResource(R.string.attach_audio_file)
                    } else {
                        stringResource(R.string.attach_another_audio_file)
                    },
                style = TitleMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
            )
        }

        Spacer(modifier = Modifier.height(L))

        TranscriptionContent(
            state = state,
            onIntent = onIntent,
        )
    }
}

@Composable
private fun TranscriptionContent(
    modifier: Modifier = Modifier,
    onIntent: (DemoTranscriptionIntent) -> Unit,
    state: DemoTranscriptionScreenState,
) {
    if (state.error != null) {
        ErrorComponent(
            modifier = modifier.fillMaxSize(),
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
    } else if (state.transcription.isNotBlank()) {
        MainContent(
            modifier = modifier,
            state = state,
        )
    }
}

@Composable
private fun MainContent(
    modifier: Modifier,
    state: DemoTranscriptionScreenState,
) {
    Column(
        modifier = modifier,
    ) {
        var fontSize by remember { mutableIntStateOf(BASE_FONT_SIZE) }

        TranscriptionContainer(
            modifier = Modifier.weight(1f),
            name = stringResource(R.string.transcription),
        ) {
            Text(
                modifier = Modifier.verticalScroll(rememberScrollState()).padding(horizontal = L),
                text = state.transcription,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = fontSize.sp,
                lineHeight = (fontSize * 1.5).sp,
            )
        }

        SupportBottomBar(
            modifier = Modifier.fillMaxWidth(),
            fontSize = fontSize,
            minFontSize = MIN_FONT_SIZE,
            maxFontSize = MAX_FONT_SIZE,
            onSizeChanged = { newSize ->
                fontSize = newSize
            },
        )
    }
}

@Composable
@Preview
private fun DemoTranscriptionScreenPreview() {
    AcessIFTheme {
        DemoTranscriptionScreen(
            modifier = Modifier,
            state =
                DemoTranscriptionScreenState(
                    isLoading = false,
                    transcription = "Lore ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun DemoTranscriptionScreenStartPreview() {
    AcessIFTheme {
        DemoTranscriptionScreen(
            modifier = Modifier,
            state =
                DemoTranscriptionScreenState(),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun DemoTranscriptionScreenLoadingPreview() {
    AcessIFTheme {
        DemoTranscriptionScreen(
            modifier = Modifier,
            state =
                DemoTranscriptionScreenState(
                    isLoading = true,
                ),
            onIntent = {},
        )
    }
}

@Composable
@Preview
private fun DemoTranscriptionScreenErrorPreview() {
    AcessIFTheme {
        DemoTranscriptionScreen(
            modifier = Modifier,
            state =
                DemoTranscriptionScreenState(
                    isLoading = false,
                    error = UiText.StringResource(id = R.string.no_internet),
                ),
            onIntent = {},
        )
    }
}
