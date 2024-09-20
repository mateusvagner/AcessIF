package com.mv.acessif.ui.designSystem.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.White

@Composable
fun TextContainer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Box(
        modifier =
            modifier
                .padding(horizontal = L)
                .fillMaxSize()
                .background(color = White, shape = RoundedCornerShape(8.dp)),
    ) {
        content()
    }
}
