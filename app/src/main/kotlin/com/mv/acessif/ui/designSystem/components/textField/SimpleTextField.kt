package com.mv.acessif.ui.designSystem.components.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.XS

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
    isError: Boolean,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = isError,
            singleLine = true,
        )

        if (isError && errorMessage != null) {
            Text(
                modifier = Modifier.padding(start = XS, top = XS),
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
            )
        }
    }
}

@Preview
@Composable
private fun SimpleTextFieldPreview() {
    Column(
        modifier =
            Modifier
                .background(color = LightGrey)
                .padding(S),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(S),
    ) {
        SimpleTextField(
            label = "Name",
            value = "",
            isError = false,
            onValueChange = {},
        )

        SimpleTextField(
            label = "Name",
            value = "John Doe",
            isError = false,
            onValueChange = {},
        )

        SimpleTextField(
            label = "Label",
            value = "Invalid input",
            isError = true,
            onValueChange = {},
        )

        SimpleTextField(
            label = "Label",
            value = "Invalid input",
            isError = true,
            errorMessage = "Invalid input",
            onValueChange = {},
        )
    }
}
