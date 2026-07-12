package com.example.hnotes.feature.note.api.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class NoteNavKey(val noteId: Long?): NavKey