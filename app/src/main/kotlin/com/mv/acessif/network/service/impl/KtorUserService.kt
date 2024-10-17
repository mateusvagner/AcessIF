package com.mv.acessif.network.service.impl

import com.mv.acessif.network.HttpRoutes
import com.mv.acessif.network.dto.UserDto
import com.mv.acessif.network.service.UserService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerAuthProvider
import io.ktor.client.plugins.plugin
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class KtorUserService
    @Inject
    constructor(
        private val client: HttpClient,
    ) : UserService {
        override suspend fun getUser(): UserDto {
            return client.get {
                url(HttpRoutes.USER)
            }.body()
        }

        override suspend fun logout() {
            client.plugin(Auth).providers.filterIsInstance<BearerAuthProvider>()
                .firstOrNull()?.clearToken()
        }
    }
