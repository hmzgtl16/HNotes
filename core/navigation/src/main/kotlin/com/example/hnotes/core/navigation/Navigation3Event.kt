package com.example.hnotes.core.navigation

import androidx.navigation3.runtime.NavKey

sealed interface Navigation3Event {

    data class NavigateTo(val navKey: NavKey) : Navigation3Event
    data object NavigateBack : Navigation3Event
}