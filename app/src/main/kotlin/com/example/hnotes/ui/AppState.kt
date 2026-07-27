/*
 * Copyright (c) 2026 GATTAL Hamza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.hnotes.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
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
class AppState
@OptIn(ExperimentalMaterial3AdaptiveApi::class)
constructor(val navigationState: NavigationState, val sceneStrategies: List<SceneStrategy<NavKey>>) {
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

    val directive =
        remember(windowAdaptiveInfo) {
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
