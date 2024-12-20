package com.mv.acessif.data.repository.impl

import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.data.mapper.SummaryMapper
import com.mv.acessif.data.repository.SummaryRepository
import com.mv.acessif.domain.Summary
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result
import com.mv.acessif.network.service.SummaryService
import com.mv.acessif.util.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SummaryRepositoryImpl
    @Inject
    constructor(
        private val summaryService: SummaryService,
        private val dispatcherProvider: DispatcherProvider,
    ) : SummaryRepository {
        override suspend fun summarizeTranscription(transcriptionId: Int): Result<Summary, DataError.Network> {
            return withContext(dispatcherProvider.io) {
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
