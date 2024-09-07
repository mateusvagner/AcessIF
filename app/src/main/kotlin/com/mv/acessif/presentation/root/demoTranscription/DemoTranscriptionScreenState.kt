package com.mv.acessif.presentation.root.demoTranscription

import com.mv.acessif.presentation.UiText

data class DemoTranscriptionScreenState(
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val shouldOpenFilePicker: Boolean = false,
    val transcription: String = "",
)
