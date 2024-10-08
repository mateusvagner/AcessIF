package com.mv.acessif.ui.designSystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.designSystem.components.button.BackButton
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.DarkGrey
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleLarge
import com.mv.acessif.ui.theme.XL

@Composable
fun DefaultScreenHeader(
    modifier: Modifier = Modifier,
    origin: String? = null,
    screenTitle: String? = null,
    supportIcon: (@Composable () -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    onSupportIconPressed: (() -> Unit)? = null,
) {
    Row(
        modifier =
            modifier
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(top = XL, bottom = M)
                .padding(horizontal = S),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (onBackPressed != null) {
            BackButton(
                label = origin,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier,
                onClick = { onBackPressed.invoke() },
            )
        } else if (screenTitle != null) {
            Text(
                modifier = Modifier.padding(horizontal = L),
                text = screenTitle,
                style = TitleLarge.copy(color = MaterialTheme.colorScheme.onPrimary),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (onSupportIconPressed != null && supportIcon != null) {
            IconButton(
                onClick = { onSupportIconPressed.invoke() },
            ) {
                supportIcon.invoke()
            }
        }
    }
}

@Preview
@Composable
private fun DefaultScreenHeaderPreview() {
    AcessIFTheme {
        Column(
            modifier =
                Modifier
                    .background(color = DarkGrey),
            verticalArrangement = Arrangement.spacedBy(S),
        ) {
            DefaultScreenHeader(
                screenTitle = "My Transcriptions",
            )

            DefaultScreenHeader(
                screenTitle = "My Transcriptions",
                supportIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
                    )
                },
                onSupportIconPressed = { },
            )

            DefaultScreenHeader(
                origin = "My Transcriptions",
                supportIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.ic_menu),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
                    )
                },
                onBackPressed = { },
                onSupportIconPressed = { },
            )
        }
    }
}
