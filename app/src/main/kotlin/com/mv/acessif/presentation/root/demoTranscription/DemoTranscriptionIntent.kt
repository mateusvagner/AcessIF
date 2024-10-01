package com.mv.acessif.presentation.root.demoTranscription

sealed interface DemoTranscriptionIntent {
    data object OnAttachFile : DemoTranscriptionIntent

    data object OnNavigateBack : DemoTranscriptionIntent

    data object OnShareTranscription : DemoTranscriptionIntent
}
