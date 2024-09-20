package com.mv.acessif.domain.repository

import com.mv.acessif.domain.Summary
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result

interface SummaryRepository {
    suspend fun summarizeTranscription(transcriptionId: Int): Result<Summary, DataError>
}
