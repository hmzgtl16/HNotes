package com.example.hnotes.feature.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.UserDataRepository
import com.example.hnotes.core.model.data.UiTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userDataRepository: UserDataRepository,
) : ViewModel() {


    val uiState: StateFlow<SettingsUiState> = combine(
            flow = userDataRepository.uiTheme,
            flow2 = userDataRepository.useDynamicUiTheme,
            transform = SettingsUiState::Success
        )
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = SettingsUiState.Loading,
        )

    fun updateUiTheme(uiTheme: UiTheme) = viewModelScope.launch {
        userDataRepository.setUiTheme(uiTheme = uiTheme)
    }

    fun updateDynamicUiTheme(useDynamicUiTheme: Boolean) = viewModelScope.launch {
        userDataRepository.setDynamicUiTheme(useDynamicUiTheme = useDynamicUiTheme)
    }
}

sealed interface SettingsUiState {

    data object Loading : SettingsUiState
    data class Success(val uiTheme: UiTheme, val useDynamicUiTheme: Boolean) : SettingsUiState
}

