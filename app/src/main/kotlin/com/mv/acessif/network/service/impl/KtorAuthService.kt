package com.mv.acessif.network.service.impl

import com.mv.acessif.network.HttpRoutes
import com.mv.acessif.network.dto.AccessTokenDto
import com.mv.acessif.network.dto.AuthTokenDto
import com.mv.acessif.network.dto.LoginRequestDto
import com.mv.acessif.network.dto.SignUpRequestDto
import com.mv.acessif.network.service.AuthService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class KtorAuthService
    @Inject
    constructor(private val client: HttpClient) : AuthService {
        override suspend fun postLogin(login: LoginRequestDto): AuthTokenDto {
            return client.post {
                url(HttpRoutes.LOGIN)
                contentType(ContentType.Application.Json)
                setBody(login)
            }.body()
        }

        override suspend fun postSignUp(signUp: SignUpRequestDto): AuthTokenDto {
            return client.post {
                url(HttpRoutes.SIGN_UP)
                contentType(ContentType.Application.Json)
                setBody(signUp)
            }.body()
        }

        override suspend fun postRefreshToken(refreshToken: String): AccessTokenDto {
            return client.post {
                url(HttpRoutes.REFRESH_TOKEN)
            }.body()
        }
    }
