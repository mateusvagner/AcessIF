package com.mv.acessif.data.repository

import com.mv.acessif.domain.AudioFile
import com.mv.acessif.domain.result.DataError
import com.mv.acessif.domain.result.Result

interface AudioFileRepository {
    suspend fun getAudioFiles(): Result<List<AudioFile>, DataError>
}
