package com.mv.acessif.presentation.home.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.theme.AcessIFTheme
import com.mv.acessif.ui.theme.L
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.TitleMedium
import com.mv.acessif.ui.theme.White

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    userName: String,
    onMenuPressed: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.primary)
                .padding(vertical = S)
                .padding(start = L, end = S),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.welcome_user, userName),
            style = TitleMedium.copy(color = White),
        )

        IconButton(
            onClick = onMenuPressed,
        ) {
            Image(
                painter = painterResource(R.drawable.ic_menu),
                colorFilter = ColorFilter.tint(color = White),
                contentDescription = stringResource(R.string.menu),
            )
        }
    }
}

@Preview
@Composable
private fun HomeHeaderPreview() {
    AcessIFTheme {
        HomeHeader(
            userName = "Mateus",
            onMenuPressed = {},
        )
    }
}
