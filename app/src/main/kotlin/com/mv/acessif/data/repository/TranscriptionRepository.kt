package com.mv.acessif.data.repository

import com.mv.acessif.domain.Transcription
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result
import java.io.File

interface TranscriptionRepository {
    suspend fun getTranscriptions(): Result<List<Transcription>, DataError>

    suspend fun getLastTranscriptions(): Result<List<Transcription>, DataError>

    suspend fun getTranscriptionById(id: Int): Result<Transcription, DataError>

    suspend fun transcribe(file: File): Result<Transcription, DataError>

    suspend fun transcribeId(file: File): Result<Int, DataError>

    suspend fun transcribeDemo(file: File): Result<Transcription, DataError>

    suspend fun updateTranscriptionName(
        id: Int,
        name: String,
    ): Result<Transcription, DataError>

    suspend fun getAudioUrl(audioId: String): String

    suspend fun deleteTranscription(id: Int): Result<Unit, DataError>

    suspend fun favoriteTranscription(id: Int): Result<Transcription, DataError>
}
