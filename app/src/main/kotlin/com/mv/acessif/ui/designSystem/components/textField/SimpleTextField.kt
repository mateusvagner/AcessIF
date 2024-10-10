package com.mv.acessif.ui.designSystem.components.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.ui.theme.BaseCornerRadius
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
    focusManager: FocusManager,
    onValueChange: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(BaseCornerRadius),
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = isError,
            singleLine = true,
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
            keyboardActions =
                KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                ),
            colors =
                OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                ),
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
    val focusManager = LocalFocusManager.current
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
            focusManager = focusManager,
            onValueChange = {},
        )

        SimpleTextField(
            label = "Name",
            value = "John Doe",
            isError = false,
            focusManager = focusManager,
            onValueChange = {},
        )

        SimpleTextField(
            label = "Label",
            value = "Invalid input",
            isError = true,
            focusManager = focusManager,
            onValueChange = {},
        )

        SimpleTextField(
            label = "Label",
            value = "Invalid input",
            isError = true,
            errorMessage = "Invalid input",
            focusManager = focusManager,
            onValueChange = {},
        )
    }
}
