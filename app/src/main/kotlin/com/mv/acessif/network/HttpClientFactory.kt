package com.mv.acessif.network

import android.util.Log
import com.mv.acessif.data.local.SharedPreferencesManager
import com.mv.acessif.network.dto.AccessTokenDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.headers
import io.ktor.client.request.post
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
                    loadTokens {
                        val accessToken = sharedPreferencesManager.getAccessToken()
                        val refreshToken = sharedPreferencesManager.getRefreshToken()

                        if (accessToken != null && refreshToken != null) {
                            BearerTokens(
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                            )
                        } else {
                            null
                        }
                    }

                    refreshTokens {
                        val refreshToken = sharedPreferencesManager.getRefreshToken().orEmpty()

                        val result =
                            refreshTokenClient.post {
                                markAsRefreshTokenRequest()
                                headers {
                                    append(HttpHeaders.Authorization, "Bearer $refreshToken")
                                }
                            }.body<AccessTokenDto>()

                        sharedPreferencesManager.saveAccessToken(result.accessToken)

                        BearerTokens(
                            accessToken = result.accessToken,
                            refreshToken = refreshToken,
                        )
                    }

                    sendWithoutRequest { request ->
                        request.url.encodedPath.contains(HttpRoutes.LOGIN).not() ||
                            request.url.encodedPath.contains(HttpRoutes.SIGN_UP).not()
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
        }
    }

    private val refreshTokenClient =
        HttpClient(Android) {
            expectSuccess = true

            defaultRequest { url(HttpRoutes.BASE_URL + HttpRoutes.REFRESH_TOKEN) }

            install(Logging) {
                logger =
                    object : Logger {
                        override fun log(message: String) {
                            Log.i("HttpRefreshClient", message)
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
        }
}
