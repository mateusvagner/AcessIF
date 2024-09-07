package com.mv.acessif.ui.designSystem.components.button

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.DarkGrey
import com.mv.acessif.ui.theme.S

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    label: String? = null,
    onClick: () -> Unit,
) {
    val defaultLabel =
        stringResource(
            id = R.string.close,
        )

    ButtonComponent(
        modifier = modifier,
        trailingImage = {
            Image(
                painter = painterResource(id = R.drawable.ic_close),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Black),
            )
        },
        label = label ?: defaultLabel,
        semantics = defaultLabel,
        onClick = onClick,
    )
}

@Composable
@Preview
private fun ClosePreview() {
    Surface(
        modifier =
            Modifier
                .background(color = DarkGrey)
                .padding(S),
    ) {
        CloseButton(onClick = {})
    }
}
