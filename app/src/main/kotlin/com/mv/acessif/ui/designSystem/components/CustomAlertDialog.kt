package com.mv.acessif.ui.designSystem.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
        containerColor = MaterialTheme.colorScheme.surface,
        icon = {
            if (icon != null) {
                Icon(
                    painter = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.secondary,
                )
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
                },
            ) {
                Text(
                    text = stringResource(R.string.confirm),
                    color = MaterialTheme.colorScheme.secondary,
                    fontWeight = FontWeight.Black,
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
            ) {
                Text(
                    text = stringResource(R.string.dismiss),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                )
            }
        },
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

@Preview(uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CustomAlertDialogDarkPreview() {
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
