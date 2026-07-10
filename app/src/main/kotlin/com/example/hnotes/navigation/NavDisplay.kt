package com.example.hnotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.example.hnotes.feature.note.impl.navigation.noteEntry
import com.example.hnotes.feature.notes.impl.navigation.notesEntry
import com.example.hnotes.feature.search.impl.navigation.searchEntry
import com.example.hnotes.feature.settings.impl.navigation.settingsEntry
import com.example.hnotes.ui.AppState

@Composable
fun AppNavDisplay(
    appState: AppState,
    entryProvider: (NavKey) -> NavEntry<NavKey>,
    modifier: Modifier = Modifier
) {
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