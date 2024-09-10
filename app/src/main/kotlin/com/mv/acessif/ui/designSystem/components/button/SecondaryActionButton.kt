package com.mv.acessif.ui.designSystem.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.designSystem.components.button.common.ButtonContent
import com.mv.acessif.ui.theme.BaseButtonHeight
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.PurpleGrey40
import com.mv.acessif.ui.theme.S

@Composable
fun SecondaryActionButton(
    modifier: Modifier = Modifier,
    label: String? = null,
    isEnabled: Boolean = true,
    leadingImage: (@Composable () -> Unit)? = null,
    trailingImage: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    OutlinedButton(
        modifier = modifier.sizeIn(minHeight = BaseButtonHeight),
        enabled = isEnabled,
        onClick = onClick,
    ) {
        // TODO change disabled color

        ButtonContent(
            label = label,
            mainColor = PurpleGrey40,
            leadingImage = leadingImage,
            trailingImage = trailingImage,
        )
    }
}

@Preview
@Composable
private fun SecondaryActionButtonPreview() {
    Column(
        modifier =
            Modifier
                .background(color = LightGrey)
                .padding(S),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(S),
    ) {
        SecondaryActionButton(
            label = "Button 1",
            onClick = {},
        )

        SecondaryActionButton(
            label = "Button 2",
            leadingImage = {
                Image(
                    painter =
                        painterResource(
                            id = R.drawable.ic_summarize,
                        ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = PurpleGrey40),
                )
            },
            onClick = {},
        )

        SecondaryActionButton(
            label = "Button 3",
            trailingImage = {
                Image(
                    painter =
                        painterResource(
                            id = R.drawable.ic_summarize,
                        ),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = PurpleGrey40),
                )
            },
            onClick = {},
        )
    }
}
