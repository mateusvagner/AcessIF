package com.mv.acessif.network

import android.util.Log
import com.mv.acessif.data.local.SharedPreferencesManager
import com.mv.acessif.network.dto.AccessTokenDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.http.HttpHeaders
import io.ktor.http.encodedPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(sharedPreferencesManager: SharedPreferencesManager): HttpClient {
        return HttpClient(Android) {
            expectSuccess = true

            defaultRequest { url(HttpRoutes.BASE_URL) }

            install(Auth) {
                bearer {
//                    loadTokens {
//                        sendWithoutRequest { true }
//
//                        val accessToken = sharedPreferencesManager.getAccessToken()
//                        val refreshToken = sharedPreferencesManager.getRefreshToken()
//
//                        if (accessToken != null && refreshToken != null) {
//                            BearerTokens(
//                                accessToken = accessToken,
//                                refreshToken = refreshToken,
//                            )
//                        } else {
//                            null
//                        }
//                    }

                    refreshTokens {
                        val refreshToken = sharedPreferencesManager.getRefreshToken()

                        val result =
                            client.post {
                                url(HttpRoutes.REFRESH_TOKEN)
                                markAsRefreshTokenRequest()
                                headers {
                                    remove(HttpHeaders.Authorization)
                                    append(HttpHeaders.Authorization, "Bearer $refreshToken")
                                }
                            }.body<AccessTokenDto>()

                        sharedPreferencesManager.saveAccessToken(result.accessToken)

                        BearerTokens(
                            accessToken = result.accessToken,
                            refreshToken = refreshToken.orEmpty(),
                        )
                    }
                }
            }

            install(Logging) {
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            Log.i("HttpClient", message)
                        }
                    }
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    },
                )
            }
        }.apply {
            this.plugin(HttpSend).intercept { request ->
                val accessToken = sharedPreferencesManager.getAccessToken().orEmpty()
                val refreshToken = sharedPreferencesManager.getRefreshToken().orEmpty()

                val authToken =
                    if (request.url.encodedPath.contains(HttpRoutes.REFRESH_TOKEN)) {
                        refreshToken
                    } else {
                        accessToken
                    }

                request.headers {
                    remove(HttpHeaders.Authorization)
                    append(HttpHeaders.Authorization, "Bearer $authToken")
                }

                execute(request)
            }
        }
    }
}
