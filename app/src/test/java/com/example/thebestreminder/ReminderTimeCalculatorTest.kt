package com.example.thebestreminder

import java.time.ZoneOffset
import java.time.ZonedDateTime
import org.junit.Assert.assertEquals
import org.junit.Test

class ReminderTimeCalculatorTest {
    @Test
    fun `returns same day when selected time is still ahead`() {
        val now = ZonedDateTime.of(2026, 5, 18, 8, 15, 0, 0, ZoneOffset.UTC)

        val triggerAt = ReminderTimeCalculator.nextTriggerAtMillis(
            hour = 9,
            minute = 30,
            now = now,
        )

        val expected = ZonedDateTime.of(2026, 5, 18, 9, 30, 0, 0, ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        assertEquals(expected, triggerAt)
    }

    @Test
    fun `rolls over to the next day when selected time has passed`() {
        val now = ZonedDateTime.of(2026, 5, 18, 21, 0, 0, 0, ZoneOffset.UTC)

        val triggerAt = ReminderTimeCalculator.nextTriggerAtMillis(
            hour = 9,
            minute = 0,
            now = now,
        )

        val expected = ZonedDateTime.of(2026, 5, 19, 9, 0, 0, 0, ZoneOffset.UTC)
            .toInstant()
            .toEpochMilli()
        assertEquals(expected, triggerAt)
    }
}
