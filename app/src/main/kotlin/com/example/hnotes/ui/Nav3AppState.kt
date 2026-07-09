package com.example.hnotes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.core.navigation.NavigationState
import com.example.hnotes.core.navigation.rememberNavigationState
import com.example.hnotes.feature.notes.api.navigation.NotesNavKey

@Stable
class Nav3AppState (
    val navigationState: NavigationState
) {

    val currentNavKey: NavKey
        get() = navigationState.currentKey


    val isMainDestination: Boolean
        @Composable get() = currentNavKey == NotesNavKey
}

@Composable
fun rememberNav3AppState(): Nav3AppState {
    val navigationState = rememberNavigationState(NotesNavKey)

    return remember(navigationState) {
        Nav3AppState(navigationState = navigationState)
    }
}