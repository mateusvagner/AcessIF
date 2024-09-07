package com.mv.acessif.data.mapper

import com.mv.acessif.domain.AudioFile
import com.mv.acessif.network.dto.AudioFileDto

object AudioFileMapper {
    private fun mapAudioFileDtoToAudioFile(audioFileDto: AudioFileDto): AudioFile {
        return AudioFile(
            id = audioFileDto.id,
            data = audioFileDto.data,
            transcription = TranscriptionMapper.mapTranscriptionDtoToTranscription(audioFileDto.transcription),
        )
    }

    fun mapAudioFilesDtoToAudioFiles(audioFilesDto: List<AudioFileDto>): List<AudioFile> {
        return audioFilesDto.map { mapAudioFileDtoToAudioFile(it) }
    }
}
