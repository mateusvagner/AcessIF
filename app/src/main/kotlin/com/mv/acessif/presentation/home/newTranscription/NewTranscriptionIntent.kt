package com.mv.acessif.presentation.home.newTranscription

sealed interface NewTranscriptionIntent {
    data object OnAttachFile : NewTranscriptionIntent

    data object OnNavigateBack : NewTranscriptionIntent

    data object OnShareTranscription : NewTranscriptionIntent

    data object OnNewTranscription : NewTranscriptionIntent

    data class OnSummarizeTranscription(val transcriptionId: Int) : NewTranscriptionIntent

    data class OnOpenTranscription(val transcriptionId: Int) : NewTranscriptionIntent
}
