package com.mv.acessif.network

object HttpRoutes {
    const val BASE_URL = "http://10.0.2.2:8000/"

    // Auth
    const val SIGN_UP = "signup"
    const val LOGIN = "signin"
    const val REFRESH_TOKEN = "refresh"

    // Transcription
    const val TRANSCRIPTIONS = "transcriptions"
    const val LAST_TRANSCRIPTIONS = "last-transcriptions"
    const val TRANSCRIBE = "transcribe"
    const val TRANSCRIBE_DEMO = "transcribe/demo"
    const val TRANSCRIBE_ID = "transcribe/id"

    // Audio File
    const val AUDIO_FILES = "audio-files"

    // Summary
    const val SUMMARIZE = "summarize"
}
