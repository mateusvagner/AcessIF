package com.mv.acessif.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.RippleDefaults.RippleAlpha
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mv.acessif.R
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BaseButtonHeight
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.BodyLarge
import com.mv.acessif.ui.theme.BodyMedium
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.LargeCornerRadius
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.White
import com.mv.acessif.ui.theme.XXL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInSignUpActionCard(
    modifier: Modifier = Modifier,
    onSignInPressed: () -> Unit,
    onSignupPressed: () -> Unit,
) {
    Surface(
        modifier =
            modifier
                .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(LargeCornerRadius),
    ) {
        Box {
            Image(
                modifier =
                    Modifier
                        .align(alignment = Alignment.TopEnd)
                        .offset(x = 32.dp, y = (-32).dp),
                painter = painterResource(R.drawable.img_moon_background),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1F)),
            )

            Image(
                modifier =
                    Modifier
                        .size(96.dp)
                        .align(alignment = Alignment.BottomStart)
                        .offset(x = (-32).dp, y = 32.dp),
                painter = painterResource(R.drawable.img_moon_background),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1F)),
            )

            Column(
                modifier =
                    Modifier
                        .padding(horizontal = L)
                        .padding(top = L, bottom = S),
                horizontalAlignment = Alignment.Start,
            ) {
                Text(
                    text = stringResource(R.string.signin_and_do_more),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = BodyLarge.copy(fontWeight = FontWeight.Black),
                )

                Spacer(modifier = Modifier.height(L))

                Text(
                    text = stringResource(R.string.signin_and_do_more_instruction),
                    color = MaterialTheme.colorScheme.onSurface,
                    style = BodyMedium,
                )

                Spacer(modifier = Modifier.height(XXL))
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
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        OutlinedButton(
                            modifier =
                                Modifier
                                    .fillMaxWidth()
                                    .sizeIn(minHeight = BaseButtonHeight),
                            onClick = onSignInPressed,
                        ) {
                            Text(
                                text = stringResource(id = R.string.sign_in),
                                style = BodyLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSurface,
                            )
                        }

                        TextButton(
                            onClick = onSignupPressed,
                        ) {
                            Text(
                                modifier =
                                Modifier,
                                text =
                                    buildAnnotatedString {
                                        append(stringResource(R.string.dont_have_account))
                                        append("  ")
                                        withStyle(
                                            style =
                                                SpanStyle(
                                                    fontWeight = FontWeight.Black,
                                                    textDecoration = TextDecoration.Underline,
                                                ),
                                        ) {
                                            append(stringResource(R.string.sign_up_here))
                                        }
                                    },
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun SignInSignUpActionCardPreview() {
    AcessIFTheme {
        SignInSignUpActionCard(
            onSignInPressed = {},
            onSignupPressed = {},
        )
    }
}
