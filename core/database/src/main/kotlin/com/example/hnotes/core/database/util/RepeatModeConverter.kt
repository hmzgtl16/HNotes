package com.example.hnotes.core.database.util

import androidx.room.TypeConverter

internal class RepeatModeConverter {

    @TypeConverter
    fun intToRepeatMode(value: Int?): RepeatMode? =
        value?.let(Int::asRepeatMode)


    @TypeConverter
    fun repeatModeToInt(repeatMode: RepeatMode?): Int? =
        repeatMode?.let(RepeatMode::ordinal)
}