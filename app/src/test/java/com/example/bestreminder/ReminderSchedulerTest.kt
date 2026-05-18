package com.example.bestreminder

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.ZoneId
import java.time.ZonedDateTime

class ReminderSchedulerTest {
    private val zoneId = ZoneId.of("UTC")

    @Test
    fun `next trigger stays on same day when time is ahead`() {
        val now = ZonedDateTime.of(2026, 5, 18, 8, 30, 0, 0, zoneId)

        val result = ReminderScheduler.nextTriggerMillis(now, 9, 0)

        val expected = ZonedDateTime.of(2026, 5, 18, 9, 0, 0, 0, zoneId)
            .toInstant()
            .toEpochMilli()
        assertEquals(expected, result)
    }

    @Test
    fun `next trigger rolls to following day when time has passed`() {
        val now = ZonedDateTime.of(2026, 5, 18, 9, 0, 0, 0, zoneId)

        val result = ReminderScheduler.nextTriggerMillis(now, 9, 0)

        val expected = ZonedDateTime.of(2026, 5, 19, 9, 0, 0, 0, zoneId)
            .toInstant()
            .toEpochMilli()
        assertEquals(expected, result)
    }
}
