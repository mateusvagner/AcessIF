package com.mv.acessif.presentation.home.transcriptionDetail

import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.UiText

data class TranscriptionDetailScreenState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val transcription: Transcription? = null,
    val currentPosition: Float = 0F,
)
