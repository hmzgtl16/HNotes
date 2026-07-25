package com.example.hnotes.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import androidx.navigation3.scene.SceneStrategy
import com.example.hnotes.core.navigation.NavigationState
import com.example.hnotes.core.navigation.rememberNavigationState
import com.example.hnotes.feature.notes.api.navigation.NotesNavKey

@Stable
class AppState @OptIn(ExperimentalMaterial3AdaptiveApi::class) constructor(
    val navigationState: NavigationState,
    val sceneStrategies: List<SceneStrategy<NavKey>>,
) {

    val currentNavKey: NavKey
        get() = navigationState.currentKey

    val isMainDestination: Boolean
        @Composable get() = currentNavKey is NotesNavKey
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun rememberAppState(): AppState {
    val navigationState = rememberNavigationState(NotesNavKey())

    val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()

    val directive = remember(windowAdaptiveInfo) {
        calculatePaneScaffoldDirective(windowAdaptiveInfo)
            .copy(horizontalPartitionSpacerSize = 0.dp)
    }

    val listDetailSceneStrategy = rememberListDetailSceneStrategy<NavKey>(directive = directive)
    val dialogSceneStrategy = remember { DialogSceneStrategy<NavKey>() }

    return remember(navigationState) {
        AppState(
            navigationState = navigationState,
            sceneStrategies = listOf(listDetailSceneStrategy, dialogSceneStrategy),
        )
    }
}