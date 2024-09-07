package com.mv.acessif.ui.designSystem.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mv.acessif.R
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.DarkGrey
import com.mv.acessif.ui.theme.LabelLarge
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.XS

@Composable
fun ButtonComponent(
    modifier: Modifier = Modifier,
    label: String? = null,
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
        Row(
            modifier =
                Modifier
                    .padding(horizontal = M)
                    .padding(vertical = S),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(XS),
        ) {
            if (leadingImage != null) {
                leadingImage()
            }

            if (label != null) {
                Text(
                    text = label,
                    style = LabelLarge,
                )
            }

            if (trailingImage != null) {
                trailingImage()
            }
        }
    }
}

@Composable
@Preview
private fun BackComponentPreview() {
    Column(
        modifier =
            Modifier
                .background(color = DarkGrey)
                .padding(S),
        verticalArrangement = Arrangement.spacedBy(S),
    ) {
        ButtonComponent(
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

        ButtonComponent(
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

        ButtonComponent(
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
