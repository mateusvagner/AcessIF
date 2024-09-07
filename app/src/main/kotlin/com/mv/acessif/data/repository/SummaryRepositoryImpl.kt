package com.mv.acessif.data.repository

import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.data.mapper.SummaryMapper
import com.mv.acessif.domain.Summary
import com.mv.acessif.domain.repository.SummaryRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.network.service.SummaryService
import javax.inject.Inject

class SummaryRepositoryImpl
    @Inject
    constructor(
        private val summaryService: SummaryService,
    ) : SummaryRepository {
        override suspend fun summarizeTranscription(transcriptionId: Int): Result<Summary?, DataError> {
            return try {
                val summaryDto = summaryService.postSummarize(transcriptionId)
                val summary = SummaryMapper.mapSummaryDtoToSummary(summaryDto)
                Result.Success(summary)
            } catch (e: Exception) {
                Result.Error(
                    ErrorMapper.mapExceptionToDataError(e),
                )
            }
        }
    }
