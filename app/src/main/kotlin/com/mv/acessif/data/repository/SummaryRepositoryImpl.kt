package com.mv.acessif.data.repository

import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.data.mapper.SummaryMapper
import com.mv.acessif.di.IoDispatcher
import com.mv.acessif.domain.Summary
import com.mv.acessif.domain.repository.SummaryRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.network.service.SummaryService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SummaryRepositoryImpl
    @Inject
    constructor(
        private val summaryService: SummaryService,
        @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    ) : SummaryRepository {
        override suspend fun summarizeTranscription(transcriptionId: Int): Result<Summary, DataError.Network> {
            return withContext(ioDispatcher) {
                try {
                    val summaryDto = summaryService.postSummarize(transcriptionId)
                    val summary = SummaryMapper.mapSummaryDtoToSummary(summaryDto)
                    if (summary == null) {
                        Result.Error(
                            ErrorMapper.mapNetworkExceptionToNetworkDataError(NullPointerException("Summary is null")),
                        )
                    } else {
                        Result.Success(summary)
                    }
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }
    }
