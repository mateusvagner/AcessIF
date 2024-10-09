package com.mv.acessif.ui.designSystem.components.button.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleMedium

@Composable
fun ButtonContent(
    modifier: Modifier = Modifier,
    imageSpacing: Dp = S,
    mainColor: Color,
    leadingImage: (@Composable () -> Unit)? = null,
    trailingImage: (@Composable () -> Unit)? = null,
    label: String? = null,
) {
    Row(
        modifier =
            modifier.padding(vertical = S),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(imageSpacing),
    ) {
        if (leadingImage != null) {
            leadingImage()
        }

        if (label != null) {
            Text(
                text = label,
                style = TitleMedium.copy(color = mainColor),
            )
        }

        if (trailingImage != null) {
            trailingImage()
        }
    }
}

@Preview
@Composable
private fun ButtonContentPreview() {
    AcessIFTheme {
        ButtonContent(
            mainColor = MaterialTheme.colorScheme.primary,
            label = "Button",
        )
    }
}
