package com.mv.acessif.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SummaryDto(
    val id: Int,
    @SerialName("transcription_id")
    val transcriptionId: Int,
    val text: String,
)
