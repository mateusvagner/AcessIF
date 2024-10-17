package com.mv.acessif.ui.designSystem.components

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.RippleAlpha
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
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.designSystem.components.button.BackButton
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.DarkGrey
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleLarge
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL
import com.mv.acessif.ui.theme.XXXL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultScreenHeader(
    modifier: Modifier = Modifier,
    origin: String? = null,
    screenTitle: String? = null,
    supportIcon: (@Composable () -> Unit)? = null,
    onBackPressed: (() -> Unit)? = null,
    onSupportIconPressed: (() -> Unit)? = null,
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
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                        audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                        onBackPressed.invoke()
                    },
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
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                        audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                        onSupportIconPressed.invoke()
                    },
                ) {
                    supportIcon.invoke()
                }
            } else {
                Spacer(modifier = Modifier.size(XXXL))
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
