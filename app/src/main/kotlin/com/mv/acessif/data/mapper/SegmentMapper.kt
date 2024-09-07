package com.mv.acessif.data.mapper

import com.mv.acessif.domain.Segment
import com.mv.acessif.network.dto.SegmentDto

object SegmentMapper {
    private fun mapSegmentDtoToSegment(segmentDto: SegmentDto): Segment {
        return Segment(
            id = segmentDto.id,
            start = segmentDto.start,
            end = segmentDto.end,
            text = segmentDto.text,
        )
    }

    fun mapSegmentsDtoToSegments(segmentsDto: List<SegmentDto>): List<Segment> {
        return segmentsDto.map { mapSegmentDtoToSegment(it) }
    }
}
