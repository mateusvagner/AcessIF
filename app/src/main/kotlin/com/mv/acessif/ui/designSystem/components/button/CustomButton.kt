package com.mv.acessif.ui.designSystem.components.button

import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BaseButtonHeight
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    isLightColor: Boolean = true,
    color: Color = White,
    isEnabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    val vibrator = LocalContext.current.getSystemService(Vibrator::class.java)
    val audioManager = LocalContext.current.getSystemService(AudioManager::class.java)

    val rippleConfiguration =
        RippleConfiguration(
            color = if (isLightColor) Black else White,
            rippleAlpha =
                RippleAlpha(
                    0.2f,
                    0.2f,
                    0.2f,
                    0.2f,
                ),
        )
    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
        Button(
            modifier = modifier.sizeIn(minHeight = BaseButtonHeight),
            onClick = {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                onClick()
            },
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = color,
                ),
            enabled = isEnabled,
        ) {
            content()
        }
    }
}

@Preview
@Composable
private fun CustomButtonPreview() {
    AcessIFTheme {
        CustomButton(
            onClick = {},
        ) {
            Text(
                text = "Custom Button Content",
                color = Black,
            )
        }
    }
}
