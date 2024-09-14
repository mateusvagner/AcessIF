package com.mv.acessif.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestDto(
    val name: String,
    val email: String,
    val password: String,
)
