package com.mv.acessif.presentation.home.home

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
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
import com.mv.acessif.presentation.home.home.components.SeeAllButton
import com.mv.acessif.presentation.home.home.components.TranscriptionCard
import com.mv.acessif.presentation.home.transcriptions.TranscriptionsScreen
import com.mv.acessif.presentation.root.RootGraph
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.SmallCornerRadius
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
                    viewModel.handleFileUri(
                        uri,
                        context = context,
                    )
                } else {
                    viewModel.handleFileUriError()
                }
            }

        HomeScreen(
            modifier = modifier,
            userName = "", // TODO
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
        var isContextMenuVisible by rememberSaveable { mutableStateOf(false) }

        DefaultScreenHeader(
            modifier =
                Modifier
                    .background(color = MaterialTheme.colorScheme.primary),
            screenTitle = stringResource(R.string.welcome_user, userName),
            supportIcon = {
                MenuComponent(
                    isContextMenuVisible = isContextMenuVisible,
                    onDismissRequest = { isContextMenuVisible = false },
                    onIntent = onIntent,
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
                    )
                }
            }
        }
    }
}

@Composable
private fun MenuComponent(
    isContextMenuVisible: Boolean,
    onDismissRequest: () -> Unit,
    onIntent: (HomeIntent) -> Unit,
) {
    Box {
        Image(
            painter = painterResource(id = R.drawable.ic_menu),
            colorFilter = ColorFilter.tint(color = White),
            contentDescription = stringResource(R.string.menu),
        )

        DropdownMenu(
            offset = DpOffset(y = 0.dp, x = (-2).dp),
            containerColor = MaterialTheme.colorScheme.surface,
            shape = RoundedCornerShape(SmallCornerRadius),
            expanded = isContextMenuVisible,
            onDismissRequest = onDismissRequest,
        ) {
            DropdownMenuItem(
                text = {
                    Text(text = stringResource(R.string.logout))
                },
                trailingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_logout),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface),
                        contentDescription = stringResource(R.string.logout),
                    )
                },
                onClick = {
                    onIntent(HomeIntent.OnLogout)
                    onDismissRequest()
                },
            )
            // TODO Uncomment when about us screen is implemented
//            DropdownMenuItem(
//                text = {
//                    Text(text = stringResource(R.string.about_us))
//                },
//                trailingIcon = {
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_contacts),
//                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSurface),
//                        contentDescription = stringResource(R.string.about_us),
//                    )
//                },
//                onClick = {
//                    onIntent(HomeIntent.OnAboutUs)
//                    onDismissRequest()
//                },
//            )
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
            userName = "",
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
            userName = "",
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
            userName = "",
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
            userName = "",
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
