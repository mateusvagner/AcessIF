package com.mv.acessif.network.service

import com.mv.acessif.network.dto.SummaryDto

interface SummaryService {
    suspend fun postSummarize(transcriptionId: Int): SummaryDto
}
