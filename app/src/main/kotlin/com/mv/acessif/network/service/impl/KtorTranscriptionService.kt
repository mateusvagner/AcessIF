package com.mv.acessif.network.service.impl

import com.mv.acessif.network.HttpRoutes
import com.mv.acessif.network.HttpRoutes.AUDIO_FILES
import com.mv.acessif.network.HttpRoutes.BASE_URL
import com.mv.acessif.network.dto.NewNameDto
import com.mv.acessif.network.dto.TranscriptionDto
import com.mv.acessif.network.dto.TranscriptionIdDto
import com.mv.acessif.network.service.TranscriptionService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
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

        override suspend fun getLastTranscriptions(): List<TranscriptionDto> {
            return client.get {
                url(HttpRoutes.LAST_TRANSCRIPTIONS)
            }.body()
        }

        override suspend fun getTranscriptionById(id: Int): TranscriptionDto {
            return client.get {
                url("${HttpRoutes.TRANSCRIPTIONS}/$id")
            }.body()
        }

        override suspend fun postTranscribe(file: File): TranscriptionDto {
            return client.post {
                url(HttpRoutes.TRANSCRIBE)
                setBody(file.readChannel())
            }.body()
        }

        override suspend fun postTranscribeId(file: File): TranscriptionIdDto {
            return client.post {
                url(HttpRoutes.TRANSCRIBE_ID)
                setBody(file.readChannel())
            }.body()
        }

        override suspend fun postTranscribeDemo(file: File): TranscriptionDto {
            return client.post {
                url(HttpRoutes.TRANSCRIBE_DEMO)
                setBody(file.readChannel())
            }.body()
        }

        override suspend fun putUpdateTranscriptionName(
            id: Int,
            name: NewNameDto,
        ): TranscriptionDto {
            return client.put {
                url("${HttpRoutes.TRANSCRIPTIONS}/$id/name")
                setBody(name)
            }.body()
        }

        override fun getAudioUrl(audioId: String): String {
            return "${BASE_URL}/${AUDIO_FILES}/$audioId"
        }

        override suspend fun deleteTranscription(id: Int) {
            return client.delete {
                url("${HttpRoutes.TRANSCRIPTIONS}/$id")
            }.body()
        }
    }
