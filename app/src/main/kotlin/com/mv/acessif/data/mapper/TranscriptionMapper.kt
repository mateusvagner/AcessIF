package com.mv.acessif.data.mapper

import com.mv.acessif.domain.Transcription
import com.mv.acessif.network.dto.TranscriptionDto

object TranscriptionMapper {
    fun mapTranscriptionDtoToTranscription(transcriptionDto: TranscriptionDto): Transcription {
        return Transcription(
            audioId = transcriptionDto.audioId,
            id = transcriptionDto.id,
            language = transcriptionDto.language,
            segments = SegmentMapper.mapSegmentsDtoToSegments(transcriptionDto.segments),
            summary = SummaryMapper.mapSummaryDtoToSummary(transcriptionDto.summary),
            text = transcriptionDto.text,
        )
    }

    fun mapTranscriptionsDtoToTranscriptions(transcriptionsDto: List<TranscriptionDto>): List<Transcription> {
        return transcriptionsDto.map { mapTranscriptionDtoToTranscription(it) }
    }
}
