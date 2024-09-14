package com.mv.acessif.presentation.home.newTranscription

import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.UiText

data class NewTranscriptionScreenState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val transcription: Transcription? = null,
)
