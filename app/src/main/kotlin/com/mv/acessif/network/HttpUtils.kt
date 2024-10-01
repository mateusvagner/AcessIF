package com.mv.acessif.network

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

object HttpUtils {
    fun HttpRequestBuilder.addOptionalAuthHeader(token: String?) {
        token?.let {
            header(HttpHeaders.Authorization, "Bearer $it")
        }
    }

    fun HttpRequestBuilder.removeAuthHeader() {
        headers.remove(HttpHeaders.Authorization)
    }
}
