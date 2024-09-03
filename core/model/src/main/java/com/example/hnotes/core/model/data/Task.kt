package com.example.hnotes.core.model.data

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

data class Task(
    val id: Long = 0L,
    val title: String = "",
    val created: Instant = Clock.System.now(),
    val updated: Instant = Clock.System.now(),
    val reminder: Instant = Clock.System.now().plus(duration = 1.minutes),
    val repeatMode: RepeatMode = RepeatMode.NONE,
    val isCompleted: Boolean = false,
)

fun Task.isExpired(): Boolean {

    if (isCompleted) return false

    return reminder < Clock.System.now()
}
