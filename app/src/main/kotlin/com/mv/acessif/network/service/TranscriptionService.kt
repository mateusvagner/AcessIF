package com.mv.acessif.network.service

import com.mv.acessif.network.dto.TranscriptionDto
import java.io.File

interface TranscriptionService {
    suspend fun getTranscriptions(): List<TranscriptionDto>

    suspend fun getTranscriptionById(id: Int): TranscriptionDto

    suspend fun postTranscribe(
        file: File,
        isAuthorized: Boolean,
    ): TranscriptionDto
}
