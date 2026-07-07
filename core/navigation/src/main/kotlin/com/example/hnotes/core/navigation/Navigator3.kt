package com.example.hnotes.core.navigation

import androidx.navigation3.runtime.NavKey

class Navigator3(
    val state: NavigationState
) {

    fun navigateTo(navKey: NavKey) {
        state.backStack.apply {
            remove(navKey)
            add(navKey)
        }
    }

    fun navigateBack() {
        state.backStack.removeLastOrNull()
    }
}