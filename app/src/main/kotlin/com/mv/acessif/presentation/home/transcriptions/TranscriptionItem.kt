package com.mv.acessif.presentation.home.transcriptions

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mv.acessif.R
import com.mv.acessif.domain.Language
import com.mv.acessif.domain.Segment
import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.util.formatTo
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BaseCornerRadius
import com.mv.acessif.ui.theme.DarkGrey
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleSmall
import java.time.Instant
import java.util.Date

@Composable
fun TranscriptionItem(
    modifier: Modifier = Modifier,
    transcription: Transcription,
    onClick: (Transcription) -> Unit,
    onDeleteClick: (Transcription) -> Unit,
) {
    val formatedDate =
        transcription.createdAt?.formatTo("dd/MM/yyyy")
            ?: stringResource(id = R.string.unknown_date)
    val semantics = stringResource(id = R.string.open_transcription_from, transcription.name)

    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = semantics
                    role = Role.Button
                },
        color = MaterialTheme.colorScheme.surface,
        onClick = { onClick(transcription) },
        shape = RoundedCornerShape(BaseCornerRadius),
        shadowElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier.padding(M),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_file_open),
                colorFilter = ColorFilter.tint(color = DarkGrey),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.width(S))

            Text(
                modifier = Modifier.weight(1f).padding(end = M),
                text = transcription.name,
                maxLines = 2,
                style = TitleSmall,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface,
            )

            IconButton(
                onClick = { onDeleteClick(transcription) },
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_delete),
                    colorFilter = ColorFilter.tint(color = DarkGrey),
                    contentDescription = stringResource(R.string.delete_transcription),
                )
            }
        }
    }
}

@Preview
@Composable
private fun TranscriptionItemPreview() {
    AcessIFTheme {
        TranscriptionItem(
            modifier = Modifier.padding(M),
            transcription =
                Transcription(
                    audioId = "audioId.mp3",
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
            onClick = { },
            onDeleteClick = { },
        )
    }
}