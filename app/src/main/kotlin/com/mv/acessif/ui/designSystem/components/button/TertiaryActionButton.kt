package com.mv.acessif.ui.designSystem.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.designSystem.components.button.common.ButtonContent
import com.mv.acessif.ui.theme.BaseButtonHeight
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.LightSecondary
import com.mv.acessif.ui.theme.S

@Composable
fun TertiaryActionButton(
    modifier: Modifier = Modifier,
    label: String? = null,
    isEnabled: Boolean = true,
    leadingImage: (@Composable () -> Unit)? = null,
    trailingImage: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier.sizeIn(minHeight = BaseButtonHeight),
        enabled = isEnabled,
        colors =
            ButtonDefaults.buttonColors(
                containerColor = LightSecondary,
            ),
        onClick = onClick,
    ) {
        ButtonContent(
            label = label,
            mainColor = LightGrey,
            leadingImage = leadingImage,
            trailingImage = trailingImage,
        )
    }
}

@Preview
@Composable
private fun TertiaryActionButtonPreview() {
    Column(
        modifier =
            Modifier
                .background(color = LightGrey)
                .padding(S),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(S),
    ) {
        TertiaryActionButton(
            label = "Button 1",
            isEnabled = false,
            onClick = {},
        )

        TertiaryActionButton(
            label = "Button 2",
            leadingImage = {
                Image(
                    painter =
                        painterResource(
                            id = R.drawable.ic_summarize,
                        ),
                    contentDescription = null,
                )
            },
            onClick = {},
        )

        TertiaryActionButton(
            label = "Button 3",
            trailingImage = {
                Image(
                    painter =
                        painterResource(
                            id = R.drawable.ic_summarize,
                        ),
                    contentDescription = null,
                )
            },
            onClick = {},
        )
    }
}
