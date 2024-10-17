package com.mv.acessif.ui.designSystem.components.textField

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.theme.BaseCornerRadius
import com.mv.acessif.ui.theme.LightGrey
import com.mv.acessif.ui.theme.S
import com.mv.acessif.ui.theme.XS

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String,
    password: String,
    isVisible: Boolean,
    isError: Boolean,
    errorMessage: String? = null,
    focusManager: FocusManager,
    onVisibilityChanged: (Boolean) -> Unit,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
    ) {
        val stateVisible = stringResource(id = R.string.show_password_state)
        val stateNotVisible = stringResource(id = R.string.hide_password_state)

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(BaseCornerRadius),
            value = password,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = isError,
            visualTransformation =
                if (isVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
            trailingIcon = {
                IconButton(onClick = { onVisibilityChanged(!isVisible) }) {
                    Image(
                        modifier =
                            Modifier.semantics {
                                stateDescription = if (isVisible) stateVisible else stateNotVisible
                            },
                        painter =
                            if (isVisible) {
                                painterResource(
                                    id = R.drawable.ic_visibility,
                                )
                            } else {
                                painterResource(
                                    id = R.drawable.ic_visibility_off,
                                )
                            },
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onBackground),
                        contentDescription =
                            if (isVisible) {
                                stringResource(id = R.string.hide_password)
                            } else {
                                stringResource(id = R.string.show_password)
                            },
                    )
                }
            },
            keyboardOptions =
                KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done,
                ),
            keyboardActions =
                KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onDone()
                    },
                ),
            singleLine = true,
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
private fun PasswordTextFieldPreview() {
    val focusManager = LocalFocusManager.current
    Column(
        modifier =
            Modifier
                .background(color = LightGrey)
                .padding(S),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(S),
    ) {
        PasswordTextField(
            label = "Password",
            password = "",
            isVisible = false,
            isError = false,
            focusManager = focusManager,
            onVisibilityChanged = {},
            onValueChange = {},
        )

        PasswordTextField(
            label = "Password",
            password = "password",
            isVisible = false,
            isError = false,
            focusManager = focusManager,
            onVisibilityChanged = {},
            onValueChange = {},
        )

        PasswordTextField(
            label = "Password",
            password = "password",
            isVisible = true,
            isError = false,
            focusManager = focusManager,
            onVisibilityChanged = {},
            onValueChange = {},
        )

        PasswordTextField(
            label = "Password",
            password = "password",
            isVisible = false,
            isError = true,
            errorMessage = "Invalid password",
            focusManager = focusManager,
            onVisibilityChanged = {},
            onValueChange = {},
        )

        PasswordTextField(
            label = "Password",
            password = "password",
            isVisible = true,
            isError = true,
            errorMessage = "Invalid password",
            focusManager = focusManager,
            onVisibilityChanged = {},
            onValueChange = {},
        )
    }
}
