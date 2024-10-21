package com.mv.acessif.presentation.home.transcriptions

import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.UiText

data class TranscriptionsScreenState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val transcriptions: Map<String, List<Transcription>> = emptyMap(),
    val isDeletingTranscription: Boolean = false,
    val showDeleteTranscriptionDialog: Boolean = false,
    val searchText: String = "",
)
