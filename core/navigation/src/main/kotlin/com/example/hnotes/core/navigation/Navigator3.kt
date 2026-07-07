package com.example.hnotes.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.SharedFlow

interface Navigator3 {

    val events: SharedFlow<Navigation3Event>

    suspend fun navigateTo(navKey: NavKey)

    suspend fun navigateBack()
}
