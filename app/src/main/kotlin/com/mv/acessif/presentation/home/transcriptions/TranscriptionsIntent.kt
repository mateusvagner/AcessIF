package com.mv.acessif.presentation.home.transcriptions

sealed interface TranscriptionsIntent {
    data object OnNavigateBack : TranscriptionsIntent

    data object OnTryAgain : TranscriptionsIntent

    data object OnNewTranscription : TranscriptionsIntent

    data class OnOpenTranscriptionDetail(val transcriptionId: Int) : TranscriptionsIntent
}
