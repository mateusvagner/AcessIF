package com.mv.acessif.ui.designSystem.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mv.acessif.R
import com.mv.acessif.ui.designSystem.components.button.common.ButtonContent
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.XS

@Composable
fun TextButtonComponent(
    modifier: Modifier = Modifier,
    label: String? = null,
    color: Color = Black,
    semantics: String,
    leadingImage: (@Composable () -> Unit)? = null,
    trailingImage: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
) {
    Surface(
        color = Color.Transparent,
        modifier =
            modifier
                .sizeIn(minHeight = 48.dp)
                .semantics {
                    contentDescription = semantics
                    role = Role.Button
                }
                .clip(RoundedCornerShape(percent = 50)),
        onClick = onClick,
    ) {
        ButtonContent(
            modifier = Modifier.padding(end = S).clearAndSetSemantics { },
            label = label,
            mainColor = color,
            leadingImage = leadingImage,
            trailingImage = trailingImage,
            imageSpacing = XS,
        )
    }
}

@Composable
@Preview
private fun TextButtonComponentPreview() {
    Column(
        modifier =
            Modifier
                .background(color = LightGrey),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(S),
    ) {
        TextButtonComponent(
            label = "Back",
            semantics = "Back to previous screen",
            leadingImage = {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    colorFilter = ColorFilter.tint(Black),
                    contentDescription = null,
                )
            },
            onClick = {},
        )

        TextButtonComponent(
            label = "Close",
            semantics = "Close screen",
            trailingImage = {
                Image(
                    painter = painterResource(id = R.drawable.ic_close),
                    colorFilter = ColorFilter.tint(Black),
                    contentDescription = null,
                )
            },
            onClick = {},
        )

        TextButtonComponent(
            semantics = "Close screen",
            trailingImage = {
                Image(
                    painter = painterResource(id = R.drawable.ic_close),
                    colorFilter = ColorFilter.tint(Black),
                    contentDescription = null,
                )
            },
            onClick = {},
        )
    }
}
