package com.mv.acessif.presentation.root.demoTranscription

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.R
import com.mv.acessif.presentation.UiText
import com.mv.acessif.ui.designSystem.BaseScreenContainer
import kotlinx.serialization.Serializable

@Serializable
object DemoTranscriptionScreen

fun NavGraphBuilder.demoTranscriptionScreen(
    modifier: Modifier,
    rootNavController: NavHostController,
) {
    composable<DemoTranscriptionScreen> {
        val viewModel: DemoTranscriptionViewModel = hiltViewModel()
        val screenState by viewModel.state.collectAsState()

        DemoTranscriptionScreen(
            modifier = modifier,
            state = screenState,
            onIntent = { intent ->
                when (intent) {
                    is DemoTranscriptionIntent.OnAttachFile -> TODO()

                    is DemoTranscriptionIntent.OnFilePicked -> {
                        viewModel.handleFileUri(
                            intent.fileUri,
                            context = rootNavController.context,
                        )
                    }

                    is DemoTranscriptionIntent.OnCloseScreen -> rootNavController.popBackStack()
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
    val launcher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.OpenDocument(),
        ) { uri: Uri? ->
            uri?.let { onIntent(DemoTranscriptionIntent.OnFilePicked(fileUri = it)) }
            // TODO handle error
        }

    BaseScreenContainer(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Header {
                onIntent(DemoTranscriptionIntent.OnCloseScreen)
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading,
                onClick = { launcher.launch(arrayOf("audio/*")) },
            ) {
                if (state.isLoading) {
                    Text(
                        text = "Gerando transcrição...",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                } else {
                    Text(
                        text = "Anexar arquivo",
                        style = MaterialTheme.typography.headlineSmall,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            TranscriptionContent(
                state = state,
            )
        }
    }
}

@Composable
private fun Header(
    modifier: Modifier = Modifier,
    onClosePressed: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "Tela de Transcrição",
            style = MaterialTheme.typography.titleLarge.copy(color = Color.Black),
        )

        TextButton(onClick = onClosePressed) {
            Icon(
                modifier =
                    Modifier
                        .background(
                            color = Color.Transparent,
                            shape = MaterialTheme.shapes.small,
                        )
                        .size(36.dp),
                imageVector = Icons.Filled.Close,
                contentDescription = "Fechar Tela",
                tint = Color.Black,
            )
        }
    }
}

@Composable
private fun TranscriptionContent(
    modifier: Modifier = Modifier,
    state: DemoTranscriptionScreenState,
) {
    if (state.error != null) {
        Text(
            text = state.error.asString(),
            style = MaterialTheme.typography.headlineSmall.copy(color = Color.Red),
        )
    } else if (state.isLoading) {
        Text(
            text = "Sua transcrição está sendo gerada...",
            style = MaterialTheme.typography.headlineSmall,
        )
    } else {
        Text(
            text = state.transcription,
            style = MaterialTheme.typography.headlineSmall,
        )
    }
}

@Composable
@Preview
private fun DemoTranscriptionScreenPreview() {
    DemoTranscriptionScreen(
        modifier = Modifier,
        state =
            DemoTranscriptionScreenState(
                isLoading = false,
            ),
        onIntent = {},
    )
}

@Composable
@Preview
private fun DemoTranscriptionScreenLoadingPreview() {
    DemoTranscriptionScreen(
        modifier = Modifier,
        state =
            DemoTranscriptionScreenState(
                isLoading = true,
            ),
        onIntent = {},
    )
}

@Composable
@Preview
private fun DemoTranscriptionScreenErrorPreview() {
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
