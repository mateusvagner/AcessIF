package com.mv.acessif.data.repository

import com.mv.acessif.domain.Summary
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result

interface SummaryRepository {
    suspend fun summarizeTranscription(transcriptionId: Int): Result<Summary, DataError>
}
