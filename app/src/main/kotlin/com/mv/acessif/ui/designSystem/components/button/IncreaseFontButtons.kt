package com.mv.acessif.ui.designSystem.components.button

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mv.acessif.R
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.XL

@Composable
fun IncreaseFontButtons(
    fontSize: Int,
    minFontSize: Int,
    maxFontSize: Int,
    onSizeChanged: (Int) -> Unit,
) {
    Row(
        modifier = Modifier.padding(horizontal = XL),
        horizontalArrangement = Arrangement.End,
    ) {
        val semanticsDecreaseFontSize =
            stringResource(R.string.semantics_decrease_font_size)
        val semanticsIncreaseFontSize =
            stringResource(R.string.semantics_increase_font_size)

        Spacer(modifier = Modifier.weight(1f))

        OutlinedButton(
            modifier =
                Modifier
                    .height(48.dp)
                    .width(86.dp)
                    .semantics {
                        contentDescription = semanticsDecreaseFontSize
                    },
            onClick = {
                onSizeChanged((fontSize - 1).coerceIn(minFontSize, maxFontSize))
            },
            content = {
                Text(
                    text = "A -",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Black,
                )
            },
        )

        Spacer(modifier = Modifier.width(S))

        OutlinedButton(
            modifier =
                Modifier
                    .height(48.dp)
                    .width(86.dp)
                    .semantics {
                        contentDescription = semanticsIncreaseFontSize
                    },
            onClick = {
                onSizeChanged((fontSize + 1).coerceIn(minFontSize, maxFontSize))
            },
            content = {
                Text(
                    text = "A +",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Black,
                )
            },
        )
    }
}
