package com.example.hnotes.feature.notes.api.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class NotesNavKey(val labelIds: List<Long> = emptyList()): NavKey