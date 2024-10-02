package com.mv.acessif.presentation.util

import com.mv.acessif.domain.Transcription
import java.util.Locale

fun List<Transcription>.groupByFormattedDate(
    pattern: String = "dd/MM/yyyy",
    locale: Locale = Locale.getDefault(),
): Map<String, List<Transcription>> {
    return this.groupBy { transcription ->
        transcription.createdAt?.formatTo(pattern, locale) ?: "Unknown Date"
    }
}
