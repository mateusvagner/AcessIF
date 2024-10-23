package com.mv.acessif.presentation.home.transcriptions

import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.UiText

data class TranscriptionsScreenState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val transcriptions: Map<String, List<Transcription>> = emptyMap(),
    val favoriteTranscriptions: List<Transcription> = emptyList(),
    val transcriptionUpdateType: TranscriptionUpdateType? = null,
)

enum class TranscriptionUpdateType {
    DELETE,
    FAVORITE,
}
