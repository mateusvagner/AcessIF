package com.mv.acessif.ui.designSystem.components.textField

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.XS

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    label: String,
    email: String,
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
            value = email,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = isError,
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next,
                ),
            keyboardActions =
                KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) },
                ),
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
private fun EmailTextFieldPreview() {
    val focusManager = LocalFocusManager.current
    Column(
        modifier =
            Modifier
                .background(color = LightGrey)
                .padding(S),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(S),
    ) {
        EmailTextField(
            label = "E-mail",
            email = "",
            isError = false,
            focusManager = focusManager,
            onValueChange = {},
        )

        EmailTextField(
            label = "E-mail",
            email = "mateus@mail.com",
            isError = false,
            focusManager = focusManager,
            onValueChange = {},
        )

        EmailTextField(
            label = "E-mail",
            email = "mateus.com",
            isError = true,
            focusManager = focusManager,
            onValueChange = {},
        )

        EmailTextField(
            label = "E-mail",
            email = "mateus.com",
            isError = true,
            errorMessage = "E-mail is invalid",
            focusManager = focusManager,
            onValueChange = {},
        )
    }
}
