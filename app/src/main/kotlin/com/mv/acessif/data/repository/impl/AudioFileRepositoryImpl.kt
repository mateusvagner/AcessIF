package com.mv.acessif.data.repository.impl

import com.mv.acessif.data.mapper.AudioFileMapper
import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.data.repository.AudioFileRepository
import com.mv.acessif.domain.AudioFile
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result
import com.mv.acessif.network.service.AudioFileService
import com.mv.acessif.util.DispatcherProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioFileRepositoryImpl
    @Inject
    constructor(
        private val audioFileService: AudioFileService,
        private val dispatcherProvider: DispatcherProvider,
    ) : AudioFileRepository {
        override suspend fun getAudioFiles(): Result<List<AudioFile>, DataError.Network> {
            return withContext(dispatcherProvider.io) {
                try {
                    val audioFilesDto = audioFileService.getAudioFiles()
                    val audioFile = AudioFileMapper.mapAudioFilesDtoToAudioFiles(audioFilesDto)
                    Result.Success(audioFile)
                } catch (e: Exception) {
                    Result.Error(
                        ErrorMapper.mapNetworkExceptionToNetworkDataError(e),
                    )
                }
            }
        }
    }
