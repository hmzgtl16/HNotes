package com.example.hnotes.core.model

import kotlin.time.Clock
import kotlin.time.Instant

data class Note(
    val id: Long = 0L,
    val title: String = "",
    val content: String = "",
    val pinned: Boolean = false,
    val backgroundColor: Int? = null,
    val reminder: Reminder? = null,
    val items: List<Item> = emptyList(),
    val labels: List<Label> = emptyList(),
    val created: Instant = Clock.System.now(),
    val updated: Instant = Clock.System.now()
)
