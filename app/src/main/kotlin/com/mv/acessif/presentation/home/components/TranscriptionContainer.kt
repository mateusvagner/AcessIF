package com.mv.acessif.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BodyMedium
import com.mv.acessif.ui.theme.BodySmall
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.TitleLarge
import com.mv.acessif.ui.theme.XL

@Composable
fun TranscriptionContainer(
    modifier: Modifier = Modifier,
    name: String,
    createdAt: String? = null,
    onEditTranscriptionName: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
                .padding(horizontal = L),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = name,
                style = TitleLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )

            if (onEditTranscriptionName != null) {
                IconButton(
                    onClick = onEditTranscriptionName,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = stringResource(R.string.edit_transcription_name),
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    )
                }
            }
        }

        createdAt?.let {
            Text(
                text = stringResource(R.string.transcribed_at, it),
                style = BodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }

        Spacer(modifier = Modifier.padding(top = XL))

        content()
    }
}

@Preview
@Composable
private fun TranscriptionContainerPreview() {
    AcessIFTheme {
        TranscriptionContainer(
            name = "Transcription Name",
            createdAt = "10/02/2024",
            onEditTranscriptionName = {},
            content = {
                Text(
                    text = "Transcription content",
                    style = BodySmall,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            },
        )
    }
}
