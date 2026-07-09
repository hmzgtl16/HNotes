package com.example.hnotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.hnotes.feature.notes.impl.navigation.notesEntry
import com.example.hnotes.ui.Nav3AppState

@Composable
fun AppNavDisplay(
    appState: Nav3AppState,
    modifier: Modifier = Modifier
) {
    val entryProvider = entryProvider {
        notesEntry()
    }

    NavDisplay(
        backStack = appState.navigationState.backStack,
        onBack = appState.navigationState.backStack::removeLastOrNull,
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider,
    )
}