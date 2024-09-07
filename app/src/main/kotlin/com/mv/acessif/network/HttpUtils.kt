package com.mv.acessif.network

import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders

object HttpUtils {
    // TODO check this
    fun HttpRequestBuilder.addOptionalAuthHeader(accessToken: String?) {
        accessToken?.let {
            header(HttpHeaders.Authorization, "Bearer $it")
        }
    }

    fun HttpRequestBuilder.removeAuthHeader() {
        headers.remove(HttpHeaders.Authorization)
    }
}
