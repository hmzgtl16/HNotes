package com.example.hnotes.core.model

import kotlin.time.Instant
import kotlin.time.Clock

data class SearchQuery(
    val query: String,
    val queried: Instant = Clock.System.now()
)
