package com.mv.acessif.data.repository

import com.mv.acessif.data.mapper.AudioFileMapper
import com.mv.acessif.data.mapper.ErrorMapper
import com.mv.acessif.domain.AudioFile
import com.mv.acessif.domain.repository.AudioFileRepository
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result
import com.mv.acessif.network.service.AudioFileService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AudioFileRepositoryImpl
    @Inject
    constructor(
        private val audioFileService: AudioFileService,
    ) : AudioFileRepository {
        override suspend fun getAudioFiles(): Result<List<AudioFile>, DataError.Network> {
            return withContext(Dispatchers.IO) {
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
