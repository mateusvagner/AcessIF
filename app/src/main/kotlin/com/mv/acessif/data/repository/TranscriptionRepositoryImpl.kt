package com.mv.acessif.data.repository

import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.data.mapper.TranscriptionMapper
import com.mv.acessif.domain.Transcription
import com.mv.acessif.domain.repository.TranscriptionRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.network.dto.NewNameDto
import com.mv.acessif.network.service.TranscriptionService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

class TranscriptionRepositoryImpl
    @Inject
    constructor(
        private val transcriptionService: TranscriptionService,
    ) : TranscriptionRepository {
        override suspend fun getTranscriptions(): Result<List<Transcription>, DataError> {
            return withContext(Dispatchers.IO) {
                try {
                    val transcriptionsDto = transcriptionService.getTranscriptions()
                    val transcriptions =
                        TranscriptionMapper.mapTranscriptionsDtoToTranscriptions(transcriptionsDto)
                    Result.Success(transcriptions)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }

        override suspend fun getLastTranscriptions(): Result<List<Transcription>, DataError> {
            return withContext(Dispatchers.IO) {
                try {
                    val transcriptionsDto = transcriptionService.getLastTranscriptions()
                    val transcriptions =
                        TranscriptionMapper.mapTranscriptionsDtoToTranscriptions(transcriptionsDto)
                    Result.Success(transcriptions)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }

        override suspend fun getTranscriptionById(id: Int): Result<Transcription, DataError> {
            return withContext(Dispatchers.IO) {
                try {
                    val transcriptionDto = transcriptionService.getTranscriptionById(id)
                    val transcription =
                        TranscriptionMapper.mapTranscriptionDtoToTranscription(transcriptionDto)
                    Result.Success(transcription)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }

        override suspend fun transcribe(file: File): Result<Transcription, DataError> {
            return withContext(Dispatchers.IO) {
                try {
                    val transcriptionDto =
                        transcriptionService.postTranscribe(file = file)
                    val transcription =
                        TranscriptionMapper.mapTranscriptionDtoToTranscription(transcriptionDto)
                    Result.Success(transcription)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }

        override suspend fun transcribeId(file: File): Result<Int, DataError> {
            return withContext(Dispatchers.IO) {
                try {
                    val transcriptionIdDto = transcriptionService.postTranscribeId(file = file)
                    Result.Success(transcriptionIdDto.id)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }

        override suspend fun transcribeDemo(file: File): Result<Transcription, DataError> {
            return withContext(Dispatchers.IO) {
                try {
                    val transcriptionDto = transcriptionService.postTranscribeDemo(file = file)
                    val transcription =
                        TranscriptionMapper.mapTranscriptionDtoToTranscription(transcriptionDto)
                    Result.Success(transcription)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }

        override suspend fun updateTranscriptionName(
            id: Int,
            name: String,
        ): Result<Transcription, DataError> {
            return withContext(Dispatchers.IO) {
                try {
                    val transcriptionDto =
                        transcriptionService.putUpdateTranscriptionName(
                            id = id,
                            name = NewNameDto(name = name),
                        )
                    val transcription =
                        TranscriptionMapper.mapTranscriptionDtoToTranscription(transcriptionDto)
                    Result.Success(transcription)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }

        override fun getAudioUrl(audioId: String): String {
            return transcriptionService.getAudioUrl(audioId)
        }

        override suspend fun deleteTranscription(id: Int): Result<Unit, DataError> {
            return withContext(Dispatchers.IO) {
                try {
                    transcriptionService.deleteTranscription(id)
                    Result.Success(Unit)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }
    }
