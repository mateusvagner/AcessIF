package com.mv.acessif.presentation.home.transcriptions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mv.acessif.presentation.home.home.HomeGraphTab
import kotlinx.serialization.Serializable

@Serializable
object TranscriptionsScreen : HomeGraphTab

fun NavGraphBuilder.transcriptionsScreen(
    modifier: Modifier,
    navController: NavHostController,
    rootNavController: NavHostController,
) {
    composable<TranscriptionsScreen> {
        TranscriptionScreen(modifier = modifier)
    }
}

@Composable
fun TranscriptionScreen(modifier: Modifier = Modifier) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(Color.Blue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Transcription",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
@Preview
private fun TranscriptionScreenPreview() {
    TranscriptionScreen(
        modifier = Modifier,
    )
}
