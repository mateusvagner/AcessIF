package com.mv.acessif.domain

data class AudioFile(
    val id: Int,
    val data: String,
    val transcription: Transcription,
)
