package com.mv.acessif.presentation.util

import android.icu.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatTo(
    pattern: String,
    locale: Locale = Locale.getDefault(),
): String {
    val formatter = SimpleDateFormat(pattern, locale)
    return formatter.format(this)
}
