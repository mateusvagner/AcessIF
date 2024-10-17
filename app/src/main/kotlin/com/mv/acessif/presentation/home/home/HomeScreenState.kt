package com.mv.acessif.presentation.home.home

import com.mv.acessif.domain.Transcription
import com.mv.acessif.presentation.UiText

data class HomeScreenState(
    val userName: String = "",
    val transcriptionsSectionState: TranscriptionsSectionState = TranscriptionsSectionState(),
    val isLoadingTranscription: Boolean = false,
    val errorTranscription: UiText? = null,
)

data class TranscriptionsSectionState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val transcriptions: List<Transcription> = emptyList(),
)
