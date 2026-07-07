package com.example.hnotes.feature.notes.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.notes.api.navigation.NotesNavKey
import com.example.hnotes.feature.notes.impl.NotesScreen

fun EntryProviderScope<NavKey>.notesEntry() {
    entry<NotesNavKey> {
        NotesScreen()
    }
}