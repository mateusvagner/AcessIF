package com.mv.acessif.ui.designSystem.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.BodyLarge
import com.mv.acessif.ui.theme.IconBigSize
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.M
import com.mv.acessif.ui.theme.S

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
        Loader()

        if (label?.isNotBlank() == true) {
            Text(
                modifier = Modifier.padding(M),
                text = label,
                textAlign = TextAlign.Center,
                style = BodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

@Composable
private fun Loader(modifier: Modifier = Modifier) {
    var currentRotation by remember { mutableFloatStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(key1 = Unit) {
        rotation.animateTo(
            targetValue = currentRotation + 360f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart,
                ),
        ) {
            currentRotation = value
        }
    }

    Image(
        modifier =
            modifier
                .size(IconBigSize)
                .rotate(rotation.value),
        painter = painterResource(id = R.drawable.ic_loader),
        contentDescription = null,
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
    )
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
