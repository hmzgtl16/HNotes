package com.example.hnotes.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class NavigatorImpl : Navigator {

    override val events: SharedFlow<NavigationEvent>
        field = MutableSharedFlow<NavigationEvent>()

    override suspend fun navigateTo(navKey: NavKey) {
        events.emit(value = NavigationEvent.NavigateTo(navKey = navKey))
    }

    override suspend fun navigateBack() {
        events.emit(value = NavigationEvent.NavigateBack)
    }
}