package com.mv.acessif.presentation.home.newTranscription

sealed interface NewTranscriptionIntent {
    data object OnAttachFile : NewTranscriptionIntent

    data object OnNavigateBack : NewTranscriptionIntent

    data object OnShareTranscription : NewTranscriptionIntent

    data class OnSummarizeTranscription(val transcriptionId: Int) : NewTranscriptionIntent
}
