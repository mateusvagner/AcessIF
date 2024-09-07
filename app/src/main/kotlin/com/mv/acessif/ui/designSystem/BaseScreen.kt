package com.mv.acessif.ui.designSystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.ui.theme.BaseScreenPadding

@Composable
fun BaseScreenContainer(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.White,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier =
            modifier
                .fillMaxSize()
                .background(color = backgroundColor)
                .padding(BaseScreenPadding),
    ) {
        content()
    }
}

@Composable
@Preview(device = Devices.PIXEL_4_XL, showBackground = true)
fun BaseScreenContainerPreview() {
    BaseScreenContainer {
        Box(
            modifier = Modifier.background(color = Color.Gray),
        ) {
            Text(
                text = "This is the Base Container ",
            )
        }
    }
}
