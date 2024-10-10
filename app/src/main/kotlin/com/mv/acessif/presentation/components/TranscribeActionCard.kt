package com.mv.acessif.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.designSystem.components.CustomButton
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.BodyLarge
import com.mv.acessif.ui.theme.BodyMedium
import com.mv.acessif.ui.theme.CardBrushGradient
import com.mv.acessif.ui.theme.DarkCardBrushGradient
import com.mv.acessif.ui.theme.DarkOnSurface
import com.mv.acessif.ui.theme.DarkSurface
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.LargeCornerRadius
import com.mv.acessif.ui.theme.LightPrimary
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XL

@Composable
fun TranscribeActionCard(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit,
) {
    val brush =
        if (isSystemInDarkTheme()) {
            DarkCardBrushGradient
        } else {
            CardBrushGradient
        }
    Surface(
        modifier =
            modifier
                .fillMaxWidth()
                .background(brush = brush, shape = RoundedCornerShape(LargeCornerRadius)),
        color = Color.Transparent,
        shape = RoundedCornerShape(LargeCornerRadius),
        onClick = onCardClick,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                modifier = Modifier.padding(top = L),
                painter = painterResource(R.drawable.img_trancribe_card),
                contentDescription = null,
            )

            Spacer(modifier = Modifier.height(M))

            Column(
                modifier =
                    Modifier
                        .padding(horizontal = L)
                        .padding(bottom = L),
                horizontalAlignment = Alignment.Start,
            ) {
                val textColor = if (isSystemInDarkTheme()) Black else White

                Text(
                    text = stringResource(R.string.create_transcription),
                    color = textColor,
                    style = BodyLarge.copy(fontWeight = FontWeight.Black),
                )

                Spacer(modifier = Modifier.height(S))

                Text(
                    text = stringResource(R.string.create_transcription_instruction),
                    color = textColor,
                    style = BodyMedium,
                )

                Spacer(modifier = Modifier.height(XL))

                CustomButton(
                    modifier = Modifier.fillMaxWidth(),
                    isLightColor = !isSystemInDarkTheme(),
                    color = if (isSystemInDarkTheme()) DarkSurface else White,
                    onClick = onCardClick,
                ) {
                    val contentColor = if (isSystemInDarkTheme()) DarkOnSurface else LightPrimary

                    Row(
                        modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = L, vertical = S),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_upload_file),
                            colorFilter = ColorFilter.tint(color = contentColor),
                            contentDescription = null,
                        )

                        Spacer(modifier = Modifier.width(S))

                        Text(
                            text = stringResource(R.string.send_recording),
                            style = BodyLarge.copy(fontWeight = FontWeight.Bold),
                            color = contentColor,
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TranscribeActionCardPreview() {
    AcessIFTheme {
        TranscribeActionCard(
            modifier = Modifier.padding(L),
            onCardClick = { },
        )
    }
}

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TranscribeActionCardDarkPreview() {
    AcessIFTheme {
        TranscribeActionCard(
            modifier = Modifier.padding(L),
            onCardClick = { },
        )
    }
}
