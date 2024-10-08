package com.mv.acessif.presentation.home.transcriptionDetail

sealed interface TranscriptionDetailIntent {
    data object OnNavigateBack : TranscriptionDetailIntent

    data object OnNewTranscription : TranscriptionDetailIntent

    data object OnShareTranscription : TranscriptionDetailIntent

    data object OnTryAgain : TranscriptionDetailIntent

    data class OnSummarizeTranscription(val transcriptionId: Int) : TranscriptionDetailIntent
}
