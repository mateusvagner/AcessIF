package com.mv.acessif.ui.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.ui.designSystem.components.button.BackButton
import com.mv.acessif.ui.designSystem.components.button.CloseButton
import com.mv.acessif.ui.theme.DarkGrey
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleMedium

@Composable
fun ScreenHeader(
    modifier: Modifier = Modifier,
    origin: String? = null,
    screenTitle: String,
    onBackPressed: (() -> Unit)? = null,
    onClosePressed: (() -> Unit)? = null,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            if (onBackPressed != null) {
                BackButton(
                    label = origin,
                    modifier = modifier,
                    onClick = { onBackPressed.invoke() },
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (onClosePressed != null) {
                CloseButton(
                    onClick = { onClosePressed.invoke() },
                )
            }
        }

        Text(
            modifier = Modifier.padding(horizontal = S),
            text = screenTitle,
            style = TitleMedium,
        )
    }
}

@Preview
@Composable
private fun ScreenHeaderPreview() {
    Column(
        modifier =
            Modifier
                .background(color = DarkGrey),
    ) {
        ScreenHeader(
            screenTitle = "My Transcriptions",
            onBackPressed = { },
        )

        ScreenHeader(
            screenTitle = "My Transcriptions",
            onClosePressed = { },
        )

        ScreenHeader(
            screenTitle = "My Transcriptions",
            onBackPressed = { },
            onClosePressed = { },
        )
    }
}
