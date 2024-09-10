package com.mv.acessif.domain

import java.util.Date

data class Transcription(
    val audioId: Int,
    val id: Int,
    val language: String,
    val segments: List<Segment>,
    val summary: Summary? = null,
    val text: String,
    val createdAt: Date,
)
