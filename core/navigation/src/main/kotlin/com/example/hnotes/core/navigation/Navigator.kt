package com.example.hnotes.core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.coroutines.flow.SharedFlow

interface Navigator {

    val events: SharedFlow<NavigationEvent>

    suspend fun navigateTo(navKey: NavKey)

    suspend fun navigateBack()
}
