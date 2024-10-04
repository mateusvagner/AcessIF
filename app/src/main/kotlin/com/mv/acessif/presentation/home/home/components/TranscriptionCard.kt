package com.mv.acessif.presentation.home.home.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mv.acessif.R
import com.mv.acessif.domain.Language
import com.mv.acessif.domain.Segment
import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.util.formatTo
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.LargeCornerRadius
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.ShadowElevation
import com.mv.acessif.ui.theme.TitleLarge
import com.mv.acessif.ui.theme.TitleSmall
import com.mv.acessif.ui.theme.XS
import java.time.Instant
import java.util.Date

@Composable
fun TranscriptionCard(
    modifier: Modifier = Modifier,
    transcription: Transcription,
    onTranscriptionClick: (Transcription) -> Unit,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(LargeCornerRadius),
        shadowElevation = ShadowElevation,
        onClick = { onTranscriptionClick(transcription) },
    ) {
        Column(
            modifier = Modifier.padding(M).width(180.dp).height(164.dp),
        ) {
            Text(
                text = transcription.name,
                color = MaterialTheme.colorScheme.onSurface,
                style = TitleLarge.copy(fontWeight = FontWeight.Bold),
            )

            val transcribedAt =
                transcription.createdAt?.let { stringResource(R.string.transcribed_at, it.formatTo("dd/MM/yyyy")) }

            if (transcribedAt != null) {
                Spacer(modifier = Modifier.height(XS))

                Text(
                    text = transcribedAt,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = TitleSmall,
                )
            }

            Spacer(modifier = Modifier.height(L))

            Text(
                text = transcription.text,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
                style = TitleSmall,
            )
        }
    }
}

@Preview
@Composable
private fun TranscriptionCardPreview() {
    AcessIFTheme {
        TranscriptionCard(
            transcription =
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
            onTranscriptionClick = { },
        )
    }
}
