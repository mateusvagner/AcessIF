package com.mv.acessif.domain

import java.util.Date

data class Transcription(
    val audioId: String? = null,
    val name: String,
    val id: Int,
    val language: Language,
    val segments: List<Segment>,
    val summary: Summary? = null,
    val text: String,
    val createdAt: Date? = null,
    val isFavorite: Boolean = false,
)
