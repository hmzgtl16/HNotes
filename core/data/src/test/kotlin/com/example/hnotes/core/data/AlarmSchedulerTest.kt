package com.example.hnotes.core.data

import com.example.hnotes.core.alarm.AlarmScheduler
import com.example.hnotes.core.model.RepeatMode
import kotlin.time.Instant

class AlarmSchedulerTest : AlarmScheduler {
    override fun schedule(
        id: Long,
        scheduleTime: Instant,
        repeatMode: RepeatMode
    ) {
        TODO("Not yet implemented")
    }

    override fun cancel(id: Long) {
        TODO("Not yet implemented")
    }

}
