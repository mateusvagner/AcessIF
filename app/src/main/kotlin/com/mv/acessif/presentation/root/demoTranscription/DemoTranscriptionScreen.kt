package com.mv.acessif.presentation.root.demoTranscription

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.presentation.UiText
import com.mv.acessif.presentation.util.shareTextIntent
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.ScreenHeader
import com.mv.acessif.ui.designSystem.components.button.MainActionButton
import com.mv.acessif.ui.designSystem.components.button.TertiaryActionButton
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BodyLarge
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.NeutralBackground
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL
import kotlinx.serialization.Serializable

@Serializable
object DemoTranscriptionScreen

fun NavGraphBuilder.demoTranscriptionScreen(
    modifier: Modifier,
    rootNavController: NavHostController,
) {
    composable<DemoTranscriptionScreen> {
        val viewModel: DemoTranscriptionViewModel = hiltViewModel()

        val context = rootNavController.context

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

                    DemoTranscriptionIntent.OnCloseScreen -> {
                        rootNavController.navigateUp()
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
                .background(color = NeutralBackground)
                .padding(bottom = XL),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ScreenHeader(
            screenTitle = stringResource(id = R.string.make_transcription),
            onClosePressed = { onIntent(DemoTranscriptionIntent.OnCloseScreen) },
        )

        Spacer(modifier = Modifier.height(L))

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
            onIntent(DemoTranscriptionIntent.OnAttachFile)
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
            onIntent = onIntent,
        )
    }
}

@Composable
private fun MainContent(
    modifier: Modifier,
    state: DemoTranscriptionScreenState,
    onIntent: (DemoTranscriptionIntent) -> Unit,
) {
    Column {
        var fontSize by remember { mutableIntStateOf(16) }

        val minFontSize = 12
        val maxFontSize = 36

        Row(
            modifier = Modifier.padding(horizontal = XL),
            horizontalArrangement = Arrangement.End,
        ) {
            Spacer(modifier = Modifier.weight(1f))

            val semanticsDecreaseFontSize =
                stringResource(R.string.semantics_decrease_font_size)
            val semanticsIncreaseFontSize =
                stringResource(R.string.semantics_increase_font_size)

            OutlinedButton(
                modifier =
                    Modifier
                        .height(48.dp)
                        .width(86.dp)
                        .padding(bottom = S)
                        .semantics {
                            contentDescription = semanticsDecreaseFontSize
                        },
                onClick = {
                    fontSize = (fontSize - 1).coerceIn(minFontSize, maxFontSize)
                },
                content = {
                    Text(
                        text = "A -",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                    )
                },
            )

            Spacer(modifier = Modifier.width(S))

            OutlinedButton(
                modifier =
                    Modifier
                        .height(48.dp)
                        .width(86.dp)
                        .padding(bottom = S)
                        .semantics {
                            contentDescription = semanticsIncreaseFontSize
                        },
                onClick = {
                    fontSize = (fontSize + 1).coerceIn(minFontSize, maxFontSize)
                },
                content = {
                    Text(
                        text = "A +",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                    )
                },
            )
        }

        Box(
            modifier =
                modifier
                    .padding(horizontal = L)
                    .fillMaxSize()
                    .weight(1f)
                    .background(color = White, shape = RoundedCornerShape(8.dp)),
        ) {
            Text(
                modifier =
                    Modifier
                        .padding(S)
                        .verticalScroll(rememberScrollState()),
                text = state.transcription,
                style = BodyLarge.copy(fontSize = fontSize.sp, lineHeight = (fontSize * 1.5).sp),
            )
        }

        Spacer(modifier = Modifier.height(S))

        TertiaryActionButton(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = XL),
            label = stringResource(id = R.string.share_transcription),
            isEnabled = state.transcription.isNotBlank(),
            leadingImage = {
                Image(
                    painter = painterResource(id = R.drawable.ic_share),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = White),
                )
            },
        ) {
            onIntent(DemoTranscriptionIntent.OnShareTranscription)
        }
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
                    transcription = "lore ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua",
                ),
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
