package com.example.hnotes.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class Navigator3Impl : Navigator3 {

    private val _events = MutableSharedFlow<Navigation3Event>()
    override val events: SharedFlow<Navigation3Event> = _events.asSharedFlow()



    override suspend fun navigateTo(navKey: NavKey) {
        _events.emit(value = Navigation3Event.NavigateTo(navKey = navKey))
    }

    override suspend fun navigateBack() {
        _events.emit(value = Navigation3Event.NavigateBack)
    }
}