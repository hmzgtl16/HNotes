package com.example.hnotes.feature.settings.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.UserDataRepository
import com.example.hnotes.core.model.Theme
import com.example.hnotes.core.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val navigator: Navigator,
    private val userDataRepository: UserDataRepository,
) : ViewModel() {


    val uiState: StateFlow<SettingsDialogState> = userDataRepository.userData
        .map {
            SettingsDialogState.Success(
                theme = it.theme,
                useDynamicColor = it.useDynamicColor
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = SettingsDialogState.Loading,
        )

    fun onEvent(event: SettingsDialogEvent) {
        when (event) {
            is SettingsDialogEvent.Dismiss -> navigateBack()
            is SettingsDialogEvent.DynamicColorEnabled -> updateDynamicColor(event.enabled)
            is SettingsDialogEvent.ThemeChanged -> updateTheme(event.theme)
        }
    }

    fun updateTheme(theme: Theme) = viewModelScope.launch {
        userDataRepository.setTheme(theme = theme)
    }

    fun updateDynamicColor(useDynamicColor: Boolean) = viewModelScope.launch {
        userDataRepository.setDynamicColorPreference(useDynamicColor)
    }

    private fun navigateBack() = viewModelScope.launch {
        navigator.navigateBack()
    }
}

