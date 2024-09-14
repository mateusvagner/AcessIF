package com.mv.acessif.presentation.util

import android.content.Context
import android.content.Intent

fun Context.shareTextIntent(text: String) {
    val sendIntent =
        Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

    startActivity(Intent.createChooser(sendIntent, null))
}
