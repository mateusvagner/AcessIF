package com.mv.acessif.domain.repository

import com.mv.acessif.domain.Transcription
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import java.io.File

interface TranscriptionRepository {
    suspend fun getTranscriptions(): Result<List<Transcription>, DataError>

    suspend fun postTranscribe(
        file: File,
        isAuthorized: Boolean = true,
    ): Result<Transcription, DataError>
}
