package com.example.hnotes.core.testing.util

import com.example.hnotes.core.data.utils.TimeZoneMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.datetime.TimeZone

class TestTimeZoneMonitor : TimeZoneMonitor {

    private val timeZoneFlow = MutableStateFlow(defaultTimeZone)

    override val currentTimeZone: Flow<TimeZone> = timeZoneFlow

    fun setTimeZone(zoneId: TimeZone) {
        timeZoneFlow.value = zoneId
    }

    companion object {
        val defaultTimeZone: TimeZone = TimeZone.of("Europe/Warsaw")
    }
}
