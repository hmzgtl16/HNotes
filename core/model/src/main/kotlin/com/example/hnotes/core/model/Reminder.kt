package com.example.hnotes.core.model

import kotlin.time.Instant

data class Reminder(
    val id: Long = 0L,
    val time: Instant,
    val repeatMode: RepeatMode = RepeatMode.NONE
)
