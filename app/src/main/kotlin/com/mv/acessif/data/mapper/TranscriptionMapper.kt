package com.mv.acessif.data.mapper

import com.mv.acessif.data.util.DateConverter
import com.mv.acessif.domain.Language
import com.mv.acessif.domain.Transcription
import com.mv.acessif.network.dto.TranscriptionDto

object TranscriptionMapper {
    fun mapTranscriptionDtoToTranscription(transcriptionDto: TranscriptionDto): Transcription {
        return Transcription(
            audioId = transcriptionDto.audioId,
            id = transcriptionDto.id,
            language = Language.fromShortName(transcriptionDto.language),
            segments = SegmentMapper.mapSegmentsDtoToSegments(transcriptionDto.segments),
            summary = SummaryMapper.mapSummaryDtoToSummary(transcriptionDto.summary),
            text = transcriptionDto.text,
            createdAt = transcriptionDto.createdAt?.let { DateConverter.isoStringToDate(it) },
        )
    }

    fun mapTranscriptionsDtoToTranscriptions(transcriptionsDto: List<TranscriptionDto>): List<Transcription> {
        return transcriptionsDto.map { mapTranscriptionDtoToTranscription(it) }
    }
}
