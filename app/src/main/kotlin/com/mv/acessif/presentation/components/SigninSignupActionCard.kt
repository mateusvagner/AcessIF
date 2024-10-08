package com.mv.acessif.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BaseButtonHeight
import com.mv.acessif.ui.theme.BodyLarge
import com.mv.acessif.ui.theme.BodyMedium
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.LargeCornerRadius
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.XXL

@Composable
fun SigninSignupActionCard(
    modifier: Modifier = Modifier,
    onSigninPressed: () -> Unit,
    onSignupPressed: () -> Unit,
) {
    Surface(
        modifier =
            modifier
                .fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(LargeCornerRadius),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = L).padding(top = L, bottom = S),
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

            Column {
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth().sizeIn(minHeight = BaseButtonHeight),
                    onClick = onSigninPressed,
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
                            Modifier
                                .fillMaxWidth(),
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

@Preview
@Composable
private fun SigninSignupActionCardPreview() {
    AcessIFTheme {
        SigninSignupActionCard(
            onSigninPressed = {},
            onSignupPressed = {},
        )
    }
}
