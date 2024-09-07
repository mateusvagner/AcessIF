package com.mv.acessif.network.service.impl

import com.mv.acessif.network.HttpRoutes
import com.mv.acessif.network.dto.AudioFileDto
import com.mv.acessif.network.service.AudioFileService
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import javax.inject.Inject

class KtorAudioFileService
    @Inject
    constructor(private val client: HttpClient) : AudioFileService {
        override suspend fun getAudioFiles(): List<AudioFileDto> {
            return client.get {
                url(HttpRoutes.AUDIO_FILES)
            }.body()
        }
    }
