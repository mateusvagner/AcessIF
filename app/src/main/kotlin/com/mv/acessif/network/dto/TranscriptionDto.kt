package com.mv.acessif.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TranscriptionDto(
    @SerialName("audio_id")
    val audioId: String? = null,
    val name: String? = null,
    val id: Int,
    val language: String,
    val segments: List<SegmentDto>,
    val summary: SummaryDto? = null,
    val text: String,
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("is_favorite")
    val isFavorite: Boolean = false,
)
