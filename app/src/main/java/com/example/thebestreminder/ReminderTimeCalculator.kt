package com.example.thebestreminder

import java.time.ZonedDateTime

object ReminderTimeCalculator {
    fun nextTriggerAtMillis(
        hour: Int,
        minute: Int,
        now: ZonedDateTime = ZonedDateTime.now(),
    ): Long {
        var nextTrigger = now
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0)

        if (!nextTrigger.isAfter(now)) {
            nextTrigger = nextTrigger.plusDays(1)
        }

        return nextTrigger.toInstant().toEpochMilli()
    }
}
