package com.mv.acessif.presentation.root.demoTranscription

import android.net.Uri

sealed interface DemoTranscriptionIntent {
    data object OnAttachFile : DemoTranscriptionIntent

    data class OnFilePicked(
        val fileUri: Uri,
    ) : DemoTranscriptionIntent

    data object OnCloseScreen : DemoTranscriptionIntent
}
