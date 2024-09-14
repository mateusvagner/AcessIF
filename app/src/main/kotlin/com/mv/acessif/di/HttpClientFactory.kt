package com.mv.acessif.di

import android.util.Log
import com.mv.acessif.data.local.SharedPreferencesManager
import com.mv.acessif.network.HttpRoutes
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.headers
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object HttpClientFactory {
    fun create(sharedPreferencesManager: SharedPreferencesManager): HttpClient {
        return HttpClient(Android) {
            defaultRequest { url(HttpRoutes.BASE_URL) }

//            headers {
//                sharedPreferencesManager.getAccessToken()?.let { accessToken ->
//                    append(HttpHeaders.Authorization, "Bearer $accessToken")
//                }
//            }

//            install(Auth) {
//                bearer {
//                    loadTokens {
//                        sharedPreferencesManager.getAccessToken()?.let {
//                            BearerTokens(
//                                accessToken = it,
//                                refreshToken = ""
//                            )
//                        }
//                    }
//                }
//            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d("HTTP call: ", message)
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
                request.headers {
                    sharedPreferencesManager.getAccessToken()?.let { accessToken ->
                        append(HttpHeaders.Authorization, "Bearer $accessToken")
                    }
                }
                execute(request)
            }
        }
    }
}
