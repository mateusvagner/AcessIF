package com.mv.acessif.network

object HttpRoutes {
    const val BASE_URL = "http://192.168.68.115:5000/"

    // Auth
    const val SIGN_UP = "signup"
    const val LOGIN = "signin"
    const val REFRESH_TOKEN = "refresh"

    // Transcription
    const val TRANSCRIPTIONS = "transcriptions"
    const val TRANSCRIBE = "transcribe"
    const val TRANSCRIBE_DEMO = "transcribe/demo"

    // Audio File
    const val AUDIO_FILES = "audio-files"

    // Summary
    const val SUMMARIZE = "summarize"
}
