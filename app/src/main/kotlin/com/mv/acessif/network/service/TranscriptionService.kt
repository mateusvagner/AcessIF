package com.mv.acessif.network.service

import com.mv.acessif.network.dto.NewNameDto
import com.mv.acessif.network.dto.TranscriptionDto
import java.io.File

interface TranscriptionService {
    suspend fun getTranscriptions(): List<TranscriptionDto>

    suspend fun getLastTranscriptions(): List<TranscriptionDto>

    suspend fun getTranscriptionById(id: Int): TranscriptionDto

    suspend fun postTranscribe(file: File): TranscriptionDto

    suspend fun postTranscribeDemo(file: File): TranscriptionDto

    suspend fun putUpdateTranscriptionName(
        id: Int,
        name: NewNameDto,
    ): TranscriptionDto
}
