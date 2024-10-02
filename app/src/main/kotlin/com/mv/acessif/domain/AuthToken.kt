package com.mv.acessif.domain

data class AuthToken(
    val accessToken: String,
    val refreshToken: String,
)
