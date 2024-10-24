package com.mv.acessif.presentation.home.components

import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mv.acessif.R
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleLarge
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XS

@OptIn(ExperimentalMaterial3Api::class)
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
    val vibrator = LocalContext.current.getSystemService(Vibrator::class.java)
    val audioManager = LocalContext.current.getSystemService(AudioManager::class.java)

    val rippleConfiguration =
        RippleConfiguration(
            color = if (isSystemInDarkTheme()) Black else White,
            rippleAlpha =
                RippleAlpha(
                    0.2f,
                    0.2f,
                    0.2f,
                    0.2f,
                ),
        )
    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
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
                    modifier = Modifier.padding(start = XS),
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                        audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                        onSummaryPressed?.invoke()
                    },
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(S),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            modifier = Modifier.size(28.dp),
                            painter = painterResource(id = R.drawable.ic_smart_summary),
                            contentDescription = null,
                        )

                        Text(
                            text = summaryLabel,
                            style = TitleLarge.copy(color = MaterialTheme.colorScheme.onPrimary),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.spacedBy(M),
            ) {
                IconButton(
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                        audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                        onSizeChanged((fontSize + 1).coerceIn(minFontSize, maxFontSize))
                    },
                ) {
                    Image(
                        modifier = Modifier.size(36.dp),
                        painter = painterResource(id = R.drawable.ic_increase_font),
                        contentDescription = stringResource(R.string.increase_font),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
                    )
                }

                IconButton(
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                        audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                        onSizeChanged((fontSize - 1).coerceIn(minFontSize, maxFontSize))
                    },
                ) {
                    Image(
                        modifier = Modifier.size(28.dp),
                        painter = painterResource(id = R.drawable.ic_decrease_font),
                        contentDescription = stringResource(R.string.decrease_font),
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
                    )
                }
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

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun SupportBottomBarDarkPreview() {
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
