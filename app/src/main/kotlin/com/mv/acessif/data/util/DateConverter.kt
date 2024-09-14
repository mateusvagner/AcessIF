package com.mv.acessif.data.util

import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

object DateConverter {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME

    fun isoStringToDate(isoString: String): Date {
        // Parse the ISO 8601 string into an Instant
        val instant: Instant = Instant.parse(isoString)
        // Convert Instant to Date
        return Date.from(instant)
    }

    fun isoStringToZonedDateTime(isoString: String): ZonedDateTime {
        // Parse the ISO 8601 string into a ZonedDateTime
        return ZonedDateTime.parse(isoString, formatter)
    }
}
