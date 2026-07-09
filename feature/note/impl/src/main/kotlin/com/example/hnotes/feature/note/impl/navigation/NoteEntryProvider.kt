package com.example.hnotes.feature.note.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.note.api.navigation.NoteNavKey
import com.example.hnotes.feature.note.impl.NoteScreen

fun EntryProviderScope<NavKey>.noteEntry() {
    entry<NoteNavKey> {
        NoteScreen()
    }
}