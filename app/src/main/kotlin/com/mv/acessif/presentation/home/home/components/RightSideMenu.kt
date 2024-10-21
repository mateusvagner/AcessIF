package com.mv.acessif.presentation.home.home.components

import android.media.AudioManager
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mv.acessif.R
import com.mv.acessif.ui.designSystem.components.button.CustomButton
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleMedium
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RightSideMenu(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    onLogout: () -> Unit,
    onAboutProject: () -> Unit,
    onContactUs: () -> Unit,
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
        Row {
            Spacer(
                modifier =
                    Modifier
                        .width(105.dp)
                        .fillMaxHeight()
                        .clickable { onClose() }
                        .clearAndSetSemantics { },
            )

            Column(
                modifier =
                    modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.primary),
            ) {
                Row(
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(top = L, end = M),
                    horizontalArrangement = Arrangement.End,
                ) {
                    IconButton(
                        onClick = {
                            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                            audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                            onClose()
                        },
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_close),
                            contentDescription = stringResource(R.string.close),
                        )
                    }
                }

                Column(
                    modifier =
                        Modifier
                            .padding(top = 64.dp),
                ) {
                    Image(
                        modifier =
                            Modifier
                                .padding(start = XL)
                                .size(48.dp),
                        painter = painterResource(R.drawable.img_moon),
                        contentDescription = null,
                    )

                    Spacer(modifier = Modifier.size(56.dp))

                    CustomButton(
                        modifier =
                            Modifier
                                .padding(horizontal = S),
                        color = MaterialTheme.colorScheme.primary,
                        isLightColor = isSystemInDarkTheme(),
                        onClick = {
                            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                            audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                            onAboutProject()
                        },
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Text(
                                text = stringResource(R.string.about_the_project),
                                style = TitleMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                            )
                        }
                    }

                    Spacer(modifier = Modifier.size(L))

                    Text(
                        modifier =
                            Modifier
                                .padding(start = XL),
                        text =
                            if (isSystemInDarkTheme()) {
                                stringResource(R.string.dark_mode)
                            } else {
                                stringResource(R.string.light_mode)
                            },
                        style = TitleMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                    )

                    Spacer(modifier = Modifier.size(L))

                    CustomButton(
                        modifier =
                            Modifier
                                .padding(horizontal = S),
                        color = MaterialTheme.colorScheme.primary,
                        isLightColor = isSystemInDarkTheme(),
                        onClick = {
                            vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                            audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                            onContactUs()
                        },
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start,
                        ) {
                            Text(
                                text = stringResource(R.string.contact),
                                style = TitleMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                CustomButton(
                    modifier =
                        Modifier
                            .padding(horizontal = S)
                            .padding(bottom = L),
                    color = MaterialTheme.colorScheme.primary,
                    isLightColor = isSystemInDarkTheme(),
                    onClick = {
                        vibrator.vibrate(VibrationEffect.createPredefined(VibrationEffect.EFFECT_CLICK))
                        audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK)
                        onLogout()
                    },
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_logout),
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimary),
                            contentDescription = null,
                        )

                        Spacer(modifier = Modifier.width(S))

                        Text(
                            text = stringResource(R.string.logout),
                            style = TitleMedium.copy(color = MaterialTheme.colorScheme.onPrimary),
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun RightSideMenuPreview() {
    AcessIFTheme {
        RightSideMenu(
            onClose = {},
            onLogout = {},
            onAboutProject = {},
            onContactUs = {},
        )
    }
}
