package com.example.hnotes

import com.example.hnotes.core.model.Label
import com.example.hnotes.core.model.Theme

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(
        val theme: Theme,
        val useDynamicColor: Boolean,
        val labels: List<Label> = emptyList()
    ) : MainActivityUiState {
        override val shouldUseDynamicTheme: Boolean = useDynamicColor

        override fun shouldUseDarkTheme(isSystemDarkTheme: Boolean): Boolean =
            when (theme) {
                Theme.FOLLOW_SYSTEM -> isSystemDarkTheme
                Theme.LIGHT -> false
                Theme.DARK -> true
            }
    }

    fun shouldKeepSplashScreen() = this is Loading

    val shouldUseDynamicTheme: Boolean get() = true

    fun shouldUseDarkTheme(isSystemDarkTheme: Boolean): Boolean = isSystemDarkTheme
}

