package com.mv.acessif.data.mapper

import com.mv.acessif.domain.Summary
import com.mv.acessif.network.dto.SummaryDto

object SummaryMapper {
    fun mapSummaryDtoToSummary(summaryDto: SummaryDto?): Summary? {
        if (summaryDto == null) {
            return null
        }

        return Summary(
            id = summaryDto.id,
            transcriptionId = summaryDto.transcriptionId,
            text = summaryDto.text,
        )
    }
}
