package com.mv.acessif.network.service.impl

import com.mv.acessif.network.HttpRoutes
import com.mv.acessif.network.HttpUtils.removeAuthHeader
import com.mv.acessif.network.dto.AccessTokenDto
import com.mv.acessif.network.dto.LoginRequestDto
import com.mv.acessif.network.dto.SignUpRequestDto
import com.mv.acessif.network.service.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import javax.inject.Inject

class KtorAuthService
    @Inject
    constructor(private val client: HttpClient) : AuthService {
        override suspend fun postLogin(login: LoginRequestDto): AccessTokenDto {
            return client.post {
                removeAuthHeader()
                url(HttpRoutes.LOGIN)
                setBody(login)
            }.body()
        }

        override suspend fun postSignUp(signUp: SignUpRequestDto): AccessTokenDto {
            return client.post {
                removeAuthHeader()
                url(HttpRoutes.SIGN_UP)
                setBody(signUp)
            }.body()
        }
    }
