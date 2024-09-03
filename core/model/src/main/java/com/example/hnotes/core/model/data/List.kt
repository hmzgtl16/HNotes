package com.example.hnotes.core.model.data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlin.collections.List

data class List(
    val id: Long = 0L,
    val title: String = "",
    val created: Instant = Clock.System.now(),
    val updated: Instant = Clock.System.now(),
    val items: List<Item> = emptyList(),
    val isPinned: Boolean = false,
    val backgroundColor: Int? = null
)
