package com.mv.acessif.ui.designSystem.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.designSystem.components.button.SecondaryActionButton
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BaseButtonHeight
import com.mv.acessif.ui.theme.BodyLarge
import com.mv.acessif.ui.theme.IconBigSize
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S

@Composable
fun ErrorComponent(
    modifier: Modifier = Modifier,
    message: String,
    onTryAgain: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier.background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            modifier = Modifier.size(IconBigSize),
            painter = painterResource(id = R.drawable.ic_error),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
            contentDescription = stringResource(id = R.string.an_error),
        )

        Text(
            modifier = Modifier.padding(M),
            text = message,
            textAlign = TextAlign.Center,
            style = BodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
        )

        if (onTryAgain != null) {
            OutlinedButton(
                modifier = Modifier.padding(M).sizeIn(minHeight = BaseButtonHeight),
                onClick = onTryAgain
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = M),
                    text = stringResource(id = R.string.try_again),
                    style = BodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        }
    }
}

@Preview
@Composable
private fun ErrorComponentPreview() {
    AcessIFTheme {
        Column(
            modifier =
                Modifier
                    .background(color = LightGrey)
                    .padding(S),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(S),
        ) {
            ErrorComponent(
                message = "An error occurred",
            )

            ErrorComponent(
                message = "An error occurred",
                onTryAgain = {},
            )
        }
    }
}
