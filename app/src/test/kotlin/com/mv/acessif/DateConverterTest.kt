package com.mv.acessif

import com.mv.acessif.data.util.DateConverter
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class DateConverterTest {
    private val isoDateString = "2024-09-07T12:00:00.000000Z"

    @Test
    fun `the converted Date should match the expected Date`() {
        // Arrange
        val expectedDate = Date.from(ZonedDateTime.parse(isoDateString).toInstant())
        // Act
        val actualDate = DateConverter.isoStringToDate(isoDateString)
        // Assert
        assertEquals(expectedDate, actualDate)
    }

    @Test
    fun `the converted ZonedDateTime should match the expected ZonedDateTime`() {
        // Arrange
        val expectedZonedDateTime = ZonedDateTime.parse(isoDateString)
        // Act
        val actualZonedDateTime = DateConverter.isoStringToZonedDateTime(isoDateString)
        // Assert
        assertEquals(expectedZonedDateTime, actualZonedDateTime)
    }

    @Test
    fun `the ZonedDateTime should be correctly converted to the specified -4 time zone`() {
        // Arrange
        val zonedDateTimeUtc = DateConverter.isoStringToZonedDateTime(isoDateString)

        val timeZone = ZoneId.of("America/New_York")
        val zonedDateTimeNewYork = zonedDateTimeUtc.withZoneSameInstant(timeZone)

        // Act
        val formattedZonedDateTimeNewYork =
            zonedDateTimeNewYork.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
            )

        // Assert
        assertEquals("2024-09-07T08:00:00.000-04:00", formattedZonedDateTimeNewYork)
    }

    @Test
    fun `the ZonedDateTime should be correctly converted to the specified +9 time zone`() {
        // Arrange
        val zonedDateTimeUtc = DateConverter.isoStringToZonedDateTime(isoDateString)

        val timeZone = ZoneId.of("Asia/Tokyo")
        val zonedDateTimeTokyo = zonedDateTimeUtc.withZoneSameInstant(timeZone)

        // Act
        val formattedZonedDateTimeTokyo =
            zonedDateTimeTokyo.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
            )

        // Assert
        assertEquals("2024-09-07T21:00:00.000+09:00", formattedZonedDateTimeTokyo)
    }
}
