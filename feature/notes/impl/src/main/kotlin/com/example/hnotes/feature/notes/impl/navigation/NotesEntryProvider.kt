package com.example.hnotes.feature.notes.impl.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.notes.api.navigation.NotesNavKey
import com.example.hnotes.feature.notes.impl.NotesScreen
import com.example.hnotes.feature.notes.impl.NotesViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.notesEntry() {
    entry<NotesNavKey>(metadata = ListDetailSceneStrategy.listPane()) {
        NotesScreen(
            viewModel = hiltViewModel<NotesViewModel, NotesViewModel.Factory>(
                creationCallback = { factory ->
                    factory.create(it)
                }
            )
        )
    }
}
