package com.example.hnotes.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

class Navigator3Impl : Navigator3 {

    override val events: SharedFlow<Navigation3Event>
        field = MutableSharedFlow<Navigation3Event>()

    override suspend fun navigateTo(navKey: NavKey) {
        events.emit(value = Navigation3Event.NavigateTo(navKey = navKey))
    }

    override suspend fun navigateBack() {
        events.emit(value = Navigation3Event.NavigateBack)
    }
}