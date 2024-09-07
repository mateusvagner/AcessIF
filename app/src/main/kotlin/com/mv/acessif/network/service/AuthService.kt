package com.mv.acessif.network.service

import com.mv.acessif.network.dto.AccessTokenDto
import com.mv.acessif.network.dto.LoginRequestDto
import com.mv.acessif.network.dto.SignUpRequestDto

interface AuthService {
    suspend fun postLogin(login: LoginRequestDto): AccessTokenDto

    suspend fun postSignUp(signUp: SignUpRequestDto): AccessTokenDto
}
