package com.mv.acessif.presentation.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleLarge
import com.mv.acessif.ui.theme.White

@Composable
fun SupportBottomBar(
    modifier: Modifier = Modifier,
    summaryLabel: String? = null,
    onSummaryPressed: (() -> Unit)? = null,
    fontSize: Int,
    minFontSize: Int,
    maxFontSize: Int,
    onSizeChanged: (Int) -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(vertical = M)
                .padding(end = S),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (summaryLabel != null) {
            Button(
                onClick = { onSummaryPressed?.invoke() },
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(S),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_smart_summary),
                        contentDescription = null,
                    )

                    Text(
                        text = summaryLabel,
                        style = TitleLarge.copy(color = White),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            horizontalArrangement = Arrangement.spacedBy(M),
        ) {
            IconButton(
                onClick = { onSizeChanged((fontSize + 1).coerceIn(minFontSize, maxFontSize)) },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_increase_font),
                    contentDescription = stringResource(R.string.increase_font),
                )
            }

            IconButton(
                onClick = { onSizeChanged((fontSize - 1).coerceIn(minFontSize, maxFontSize)) },
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_decrease_font),
                    contentDescription = stringResource(R.string.decrease_font),
                )
            }
        }
    }
}

@Preview
@Composable
private fun SupportBottomBarPreview() {
    AcessIFTheme {
        Column(
            modifier =
                Modifier
                    .background(color = LightGrey),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(S),
        ) {
            SupportBottomBar(
                summaryLabel = "Smart Summary",
                onSummaryPressed = {},
                fontSize = 16,
                minFontSize = 12,
                maxFontSize = 24,
                onSizeChanged = {},
            )

            SupportBottomBar(
                fontSize = 16,
                minFontSize = 12,
                maxFontSize = 24,
                onSizeChanged = {},
            )
        }
    }
}
