package com.mv.acessif.network.service.impl

import com.mv.acessif.network.HttpRoutes
import com.mv.acessif.network.HttpUtils.removeAuthHeader
import com.mv.acessif.network.dto.TranscriptionDto
import com.mv.acessif.network.service.TranscriptionService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.util.cio.readChannel
import java.io.File
import javax.inject.Inject

class KtorTranscriptionService
    @Inject
    constructor(private val client: HttpClient) : TranscriptionService {
        override suspend fun getTranscriptions(): List<TranscriptionDto> {
            return client.get {
                url(HttpRoutes.TRANSCRIPTIONS)
            }.body()
        }

        override suspend fun getTranscriptionById(id: Int): TranscriptionDto {
            return client.get {
                url("${HttpRoutes.TRANSCRIPTIONS}/$id")
            }.body()
        }

        override suspend fun postTranscribe(
            file: File,
            isAuthorized: Boolean,
        ): TranscriptionDto {
            return client.post {
                url(HttpRoutes.TRANSCRIBE)
                if (!isAuthorized) {
                    removeAuthHeader()
                }
                setBody(file.readChannel())
            }.body()
        }
    }
