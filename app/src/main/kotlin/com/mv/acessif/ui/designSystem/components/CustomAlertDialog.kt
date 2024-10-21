package com.mv.acessif.ui.designSystem.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mv.acessif.R
import com.mv.acessif.ui.theme.AcessIFTheme

@Composable
fun CustomAlertDialog(
    dialogTitle: String,
    dialogText: String,
    icon: Painter? = null,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
) {
    AlertDialog(
        icon = {
            if (icon != null) {
                Icon(painter = icon, contentDescription = null)
            }
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(stringResource(R.string.confirm))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(stringResource(R.string.dismiss))
            }
        }
    )
}

@Preview
@Composable
private fun CustomAlertDialogPreview() {
    AcessIFTheme {
        CustomAlertDialog(
            dialogTitle = "Dialog Title",
            dialogText = "Dialog Text",
            icon = painterResource(R.drawable.ic_delete),
            onDismissRequest = {},
            onConfirmation = {},
        )
    }
}