package com.example.hnotes.core.database.util

import com.example.hnotes.core.model.data.RepeatMode as ModelRepeatMode

enum class RepeatMode {
    NONE,
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY
}

fun Int?.asRepeatMode(): RepeatMode = this?.let {
    RepeatMode.entries.firstOrNull { repeatMode -> repeatMode.ordinal == this }
} ?: RepeatMode.NONE

fun RepeatMode.asModelRepeatMode(): ModelRepeatMode =
    ModelRepeatMode.entries.first { it.name == name }