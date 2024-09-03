package com.example.hnotes.core.testing.data

import com.example.hnotes.core.model.data.RepeatMode
import com.example.hnotes.core.model.data.Task
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.hours

val taskTestData = listOf(
    Task(
        id = 1,
        title = "Task 1",
        created = Clock.System.now(),
        updated = Clock.System.now(),
        reminder = Clock.System.now().minus(1.hours),
        repeatMode = RepeatMode.NONE,
        isCompleted = false
    ),
    Task(
        id = 2,
        title = "Task 2",
        created = Clock.System.now(),
        updated = Clock.System.now(),
        reminder = Clock.System.now(),
        repeatMode = RepeatMode.DAILY,
        isCompleted = false
    ),
    Task(
        id = 3,
        title = "Task 3",
        created = Clock.System.now(),
        updated = Clock.System.now(),
        reminder = Clock.System.now(),
        repeatMode = RepeatMode.WEEKLY,
        isCompleted = false
    ),
    Task(
        id = 4,
        title = "Task 4",
        created = Clock.System.now(),
        updated = Clock.System.now(),
        reminder = Clock.System.now(),
        repeatMode = RepeatMode.MONTHLY,
        isCompleted = true
    ),
    Task(
        id = 5,
        title = "Task 5",
        created = Clock.System.now(),
        updated = Clock.System.now(),
        reminder = Clock.System.now(),
        repeatMode = RepeatMode.YEARLY,
        isCompleted = true
    ),
    Task(
        id = 6,
        title = "Task 6",
        created = Clock.System.now(),
        updated = Clock.System.now(),
        reminder = Clock.System.now(),
        repeatMode = RepeatMode.YEARLY,
        isCompleted = true
    )
)