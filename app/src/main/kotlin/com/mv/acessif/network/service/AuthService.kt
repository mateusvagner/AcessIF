package com.mv.acessif.network.service

import com.mv.acessif.network.dto.AccessTokenDto
import com.mv.acessif.network.dto.AuthTokenDto
import com.mv.acessif.network.dto.LoginRequestDto
import com.mv.acessif.network.dto.SignUpRequestDto

interface AuthService {
    suspend fun postLogin(login: LoginRequestDto): AuthTokenDto

    suspend fun postSignUp(signUp: SignUpRequestDto): AuthTokenDto

    suspend fun postRefreshToken(refreshToken: String): AccessTokenDto
}
