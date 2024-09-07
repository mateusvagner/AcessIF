package com.mv.acessif.network.service

import com.mv.acessif.network.dto.AudioFileDto

interface AudioFileService {
    suspend fun getAudioFiles(): List<AudioFileDto>
}
