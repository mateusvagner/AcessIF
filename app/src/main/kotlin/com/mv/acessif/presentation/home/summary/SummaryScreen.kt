package com.mv.acessif.presentation.home.summary

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.mv.acessif.domain.Summary
import com.mv.acessif.presentation.UiText
import com.mv.acessif.presentation.home.components.SupportBottomBar
import com.mv.acessif.presentation.home.components.TranscriptionContainer
import com.mv.acessif.presentation.home.home.HomeGraph
import com.mv.acessif.presentation.util.shareTextIntent
import com.mv.acessif.ui.designSystem.components.DefaultScreenHeader
import com.mv.acessif.ui.designSystem.components.ErrorComponent
import com.mv.acessif.ui.designSystem.components.LoadingComponent
import com.mv.acessif.ui.designSystem.components.button.util.BASE_FONT_SIZE
import com.mv.acessif.ui.designSystem.components.button.util.MAX_FONT_SIZE
import com.mv.acessif.ui.designSystem.components.button.util.MIN_FONT_SIZE
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL

fun NavGraphBuilder.summaryRoute(
    modifier: Modifier,
    navController: NavHostController,
) {
    composable<HomeGraph.SummaryRoute> {
        val viewModel: SummaryViewModel = hiltViewModel()

        val context = navController.context

        SummaryScreen(
            modifier = modifier,
            state = viewModel.state.value,
            onIntent = { intent ->
                when (intent) {
                    SummaryIntent.OnNavigateBack -> {
                        navController.navigateUp()
                    }

                    SummaryIntent.OnTryAgain -> {
                        viewModel.getSummary()
                    }

                    SummaryIntent.OnShareSummary -> {
                        val summaryText = viewModel.state.value.summary?.text
                        if (summaryText != null) {
                            context.shareTextIntent(summaryText)
                        }
                    }
                }
            },
        )
    }
}

@Composable
fun SummaryScreen(
    modifier: Modifier = Modifier,
    state: SummaryScreenState,
    onIntent: (SummaryIntent) -> Unit,
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
            origin = stringResource(id = R.string.transcription_detail),
            supportIcon = {
                if (state.summary?.text.isNullOrEmpty().not()) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_share),
                        contentDescription = stringResource(R.string.share_transcription),
                        colorFilter = ColorFilter.tint(White),
                    )
                }
            },
            onBackPressed = { onIntent(SummaryIntent.OnNavigateBack) },
            onSupportIconPressed = { onIntent(SummaryIntent.OnShareSummary) },
        )

        Spacer(modifier = Modifier.height(L))

        if (state.error != null) {
            ErrorComponent(
                modifier =
                    Modifier
                        .padding(horizontal = XL)
                        .fillMaxSize(),
                message = state.error.asString(),
                onTryAgain = { onIntent(SummaryIntent.OnTryAgain) },
            )
        } else if (state.isLoading) {
            LoadingComponent(
                modifier =
                    Modifier
                        .padding(horizontal = XL)
                        .fillMaxSize(),
                label = stringResource(id = R.string.your_summary_is_being_loaded),
            )
        } else if (state.summary != null) {
            SummaryContent(
                modifier = Modifier,
                summary = state.summary,
            )
        }
    }
}

@Composable
private fun SummaryContent(
    modifier: Modifier = Modifier,
    summary: Summary,
) {
    Column(
        modifier = modifier,
    ) {
        var fontSize by remember { mutableIntStateOf(BASE_FONT_SIZE) }

        TranscriptionContainer(
            modifier = Modifier.weight(1f),
            name = stringResource(R.string.summary),
        ) {
            Text(
                modifier = Modifier.verticalScroll(rememberScrollState()).padding(horizontal = L).padding(bottom = L),
                text = summary.text,
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

@Preview
@Composable
private fun SummaryScreenPreview() {
    AcessIFTheme {
        SummaryScreen(
            state =
                SummaryScreenState(
                    summary =
                        Summary(
                            id = 1,
                            transcriptionId = 1,
                            text = "Lorem ipsum dolor sit amet, \nconsectetur adipiscing elit sed do \neiusmod tempor incididunt ut labore et dolore magna aliqua.",
                        ),
                ),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun SummaryScreenStartPreview() {
    AcessIFTheme {
        SummaryScreen(
            state =
                SummaryScreenState(),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun SummaryScreenLoadingPreview() {
    AcessIFTheme {
        SummaryScreen(
            state =
                SummaryScreenState(
                    isLoading = true,
                    error = null,
                ),
            onIntent = {},
        )
    }
}

@Preview
@Composable
private fun SummaryScreenErrorPreview() {
    AcessIFTheme {
        SummaryScreen(
            state =
                SummaryScreenState(
                    isLoading = false,
                    error = UiText.StringResource(id = R.string.no_internet),
                ),
            onIntent = {},
        )
    }
}
