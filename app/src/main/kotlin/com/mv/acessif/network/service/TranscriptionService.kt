package com.mv.acessif.network.service

import com.mv.acessif.network.dto.NewNameDto
import com.mv.acessif.network.dto.TranscriptionDto
import com.mv.acessif.network.dto.TranscriptionIdDto
import java.io.File

interface TranscriptionService {
    suspend fun getTranscriptions(): List<TranscriptionDto>

    suspend fun getLastTranscriptions(): List<TranscriptionDto>

    suspend fun getTranscriptionById(id: Int): TranscriptionDto

    suspend fun postTranscribe(file: File): TranscriptionDto

    suspend fun postTranscribeId(file: File): TranscriptionIdDto

    suspend fun postTranscribeDemo(file: File): TranscriptionDto

    suspend fun putUpdateTranscriptionName(
        id: Int,
        name: NewNameDto,
    ): TranscriptionDto

    fun getAudioUrl(audioId: String): String

    suspend fun deleteTranscription(id: Int): Unit

    suspend fun putFavoriteTranscription(id: Int): TranscriptionDto
}
