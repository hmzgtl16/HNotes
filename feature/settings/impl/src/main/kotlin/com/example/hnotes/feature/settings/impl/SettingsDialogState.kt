package com.example.hnotes.feature.settings.impl

import com.example.hnotes.core.model.Theme

sealed interface SettingsDialogState {

    data object Loading : SettingsDialogState
    data class Success(val theme: Theme, val useDynamicColor: Boolean) : SettingsDialogState
}