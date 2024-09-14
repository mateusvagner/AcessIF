package com.mv.acessif.presentation.root.demoTranscription

sealed interface DemoTranscriptionIntent {
    data object OnAttachFile : DemoTranscriptionIntent

    data object OnCloseScreen : DemoTranscriptionIntent

    data object OnShareTranscription : DemoTranscriptionIntent
}
