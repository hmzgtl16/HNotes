package com.example.hnotes.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

class NavigationState(
    val startKey: NavKey,
    val backStack: NavBackStack<NavKey>
) {
    val currentKey: NavKey by derivedStateOf { backStack.last() }
}

@Composable
fun rememberNavigationState(
    startKey: NavKey,
): NavigationState {
    val navBackStack = rememberNavBackStack(startKey)

    return remember(startKey) {
        NavigationState(
            startKey = startKey,
            backStack = navBackStack
        )
    }
}
