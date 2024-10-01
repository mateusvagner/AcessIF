package com.mv.acessif.presentation.home.home

import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.UiText

data class HomeScreenState(
    val isLoadingTranscriptions: Boolean = false,
    val transcriptionsError: UiText? = null,
    val transcriptions: List<Transcription> = emptyList(),
)
