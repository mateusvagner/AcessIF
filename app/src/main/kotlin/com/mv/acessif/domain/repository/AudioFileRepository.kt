package com.mv.acessif.domain.repository

import com.mv.acessif.domain.AudioFile
import com.mv.acessif.domain.returnModel.DataError
import com.mv.acessif.domain.returnModel.Result

interface AudioFileRepository {
    suspend fun getAudioFiles(): Result<List<AudioFile>, DataError>
}
