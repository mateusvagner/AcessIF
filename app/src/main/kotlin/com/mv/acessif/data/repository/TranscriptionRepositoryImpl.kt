package com.mv.acessif.data.repository

import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.data.mapper.TranscriptionMapper
import com.mv.acessif.domain.Transcription
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.network.service.TranscriptionService
import java.io.File
import javax.inject.Inject

class TranscriptionRepositoryImpl
    @Inject
    constructor(
        private val transcriptionService: TranscriptionService,
    ) : TranscriptionRepository {
        override suspend fun getTranscriptions(): Result<List<Transcription>, DataError> {
            return try {
                val transcriptionsDto = transcriptionService.getTranscriptions()
                val transcriptions = TranscriptionMapper.mapTranscriptionsDtoToTranscriptions(transcriptionsDto)
                Result.Success(transcriptions)
            } catch (e: Exception) {
                Result.Error(
                    ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                )
            }
        }

        override suspend fun getTranscriptionById(id: Int): Result<Transcription, DataError> {
            return try {
                val transcriptionDto = transcriptionService.getTranscriptionById(id)
                val transcription = TranscriptionMapper.mapTranscriptionDtoToTranscription(transcriptionDto)
                Result.Success(transcription)
            } catch (e: Exception) {
                Result.Error(
                    ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                )
            }
        }

        override suspend fun postTranscribe(
            file: File,
            isAuthorized: Boolean,
        ): Result<Transcription, DataError> {
            return try {
                val transcriptionDto = transcriptionService.postTranscribe(file = file, isAuthorized = isAuthorized)
                val transcription = TranscriptionMapper.mapTranscriptionDtoToTranscription(transcriptionDto)
                Result.Success(transcription)
            } catch (e: Exception) {
                Result.Error(
                    ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                )
            }
        }
    }
