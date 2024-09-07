package com.mv.acessif.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class SegmentDto(
    val id: Int,
    val start: Float,
    val end: Float,
    val text: String,
)
