package com.example.hnotes.feature.note.impl.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.note.api.navigation.NoteNavKey
import com.example.hnotes.feature.note.impl.NoteScreen
import com.example.hnotes.feature.note.impl.NoteViewModel

fun EntryProviderScope<NavKey>.noteEntry() {
    entry<NoteNavKey> {
        NoteScreen(
            viewModel = hiltViewModel<NoteViewModel, NoteViewModel.Factory>(
                creationCallback = { factory ->
                    factory.create(it)
                }
            )
        )
    }
}