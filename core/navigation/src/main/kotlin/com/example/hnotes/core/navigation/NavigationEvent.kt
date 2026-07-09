package com.example.hnotes.core.navigation

import androidx.navigation3.runtime.NavKey

sealed interface NavigationEvent {

    data class NavigateTo(val navKey: NavKey) : NavigationEvent
    data object NavigateBack : NavigationEvent
}