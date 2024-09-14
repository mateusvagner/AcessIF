package com.mv.acessif.ui.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.Black
import com.mv.acessif.ui.theme.BodyMedium
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.LightPrimary
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.ProgressIndicatorSize
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.White

@Composable
fun LoadingComponent(
    modifier: Modifier = Modifier,
    label: String? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(ProgressIndicatorSize),
            color = LightPrimary,
            trackColor = White,
        )

        if (label?.isNotBlank() == true) {
            Text(
                modifier = Modifier.padding(M),
                text = label,
                style = BodyMedium,
                color = Black,
            )
        }
    }
}

@Preview
@Composable
private fun LoadingComponentPreview() {
    AcessIFTheme {
        Column(
            modifier =
                Modifier
                    .background(color = LightGrey)
                    .padding(S),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(S),
        ) {
            LoadingComponent()
            LoadingComponent(label = "Sending your message")
        }
    }
}
