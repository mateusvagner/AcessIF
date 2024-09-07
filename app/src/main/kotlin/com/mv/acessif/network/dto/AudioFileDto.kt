package com.mv.acessif.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class AudioFileDto(
    val id: Int,
    val data: String,
    val transcription: TranscriptionDto,
)
