package com.mv.acessif.presentation.home.home.components

import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mv.acessif.R
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleSmall
import com.mv.acessif.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeeAllButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    val semantics = stringResource(R.string.see_all_my_transcription)

    val vibrator = LocalContext.current.getSystemService(Vibrator::class.java)
    val audioManager = LocalContext.current.getSystemService(AudioManager::class.java)

    val rippleConfiguration =
        RippleConfiguration(
            color = if (isSystemInDarkTheme()) White else Black,
            rippleAlpha =
                RippleAlpha(
                    0.2f,
                    0.2f,
                    0.2f,
                    0.2f,
                ),
        )

    CompositionLocalProvider(LocalRippleConfiguration provides rippleConfiguration) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier =
                modifier
                    .sizeIn(minHeight = 48.dp)
                    .semantics {
                        contentDescription = semantics
                        role = Role.Button
                    }
                    .clip(RoundedCornerShape(percent = 50)),
            onClick = {
                vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                onClick()
            },
        ) {
            Row(
                modifier =
                    modifier
                        .padding(horizontal = 10.dp)
                        .padding(vertical = S),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(S),
            ) {
                Text(
                    text = stringResource(R.string.see_all),
                    style = TitleSmall.copy(color = MaterialTheme.colorScheme.onBackground),
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_right_alt),
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                    contentDescription = null,
                )
            }
        }
    }
}

@Preview
@Composable
private fun SeeAllButtonPreview() {
    AcessIFTheme {
        SeeAllButton { }
    }
}
