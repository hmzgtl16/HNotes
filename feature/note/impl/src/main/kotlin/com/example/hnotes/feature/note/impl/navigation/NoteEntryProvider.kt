package com.example.hnotes.feature.note.impl.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.note.api.navigation.NoteNavKey
import com.example.hnotes.feature.note.impl.NoteScreen
import com.example.hnotes.feature.note.impl.NoteViewModel

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
fun EntryProviderScope<NavKey>.noteEntry() {
    entry<NoteNavKey>(metadata = ListDetailSceneStrategy.detailPane()) {
        NoteScreen(
            viewModel = hiltViewModel<NoteViewModel, NoteViewModel.Factory>(
                creationCallback = { factory ->
                    factory.create(it)
                }
            )
        )
    }
}