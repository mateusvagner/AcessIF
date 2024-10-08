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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BaseCornerRadius
import com.mv.acessif.ui.theme.BodyMedium
import com.mv.acessif.ui.theme.BodySmall
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleLarge
import com.mv.acessif.ui.theme.XL

@Composable
fun TranscriptionContainer(
    modifier: Modifier = Modifier,
    name: String,
    createdAt: String? = null,
    onEditTranscriptionName: ((String) -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
    ) {
        var isEditing by remember { mutableStateOf(false) }
        var editedName by remember { mutableStateOf(name) }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = L),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (isEditing) {
                OutlinedTextField(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(BaseCornerRadius),
                    label = { Text(stringResource(R.string.new_transcription_name)) },
                    value = editedName,
                    onValueChange = { editedName = it },
                    textStyle = TitleLarge,
                )
            } else {
                Text(
                    modifier = Modifier.weight(1f).padding(vertical = S),
                    text = editedName,
                    style = TitleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }

            val iconSemantics =
                if (isEditing) {
                    stringResource(R.string.save_transcription_name)
                } else {
                    stringResource(R.string.edit_transcription_name)
                }

            if (onEditTranscriptionName != null) {
                IconButton(
                    onClick = {
                        isEditing = !isEditing
                        if (!isEditing) {
                            onEditTranscriptionName(editedName)
                        }
                    },
                ) {
                    val icon =
                        if (isEditing) {
                            painterResource(id = R.drawable.ic_save)
                        } else {
                            painterResource(id = R.drawable.ic_edit)
                        }
                    Image(
                        painter = icon,
                        contentDescription = iconSemantics,
                        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                    )
                }
            }
        }

        if (isEditing.not()) {
            createdAt?.let {
                Text(
                    modifier = Modifier.padding(start = L),
                    text = stringResource(R.string.transcribed_at, it),
                    style = BodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
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
