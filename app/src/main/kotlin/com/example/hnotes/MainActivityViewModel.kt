package com.example.hnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.UserDataRepository
import com.example.hnotes.core.model.data.UiTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userDataRepository: UserDataRepository
) : ViewModel() {

    val uiState: StateFlow<MainActivityUiState> = combine(
        flow = userDataRepository.uiTheme,
        flow2 = userDataRepository.useDynamicUiTheme,
        transform = MainActivityUiState::Success
    ).stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
    )
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val uiTheme: UiTheme, val useDynamicUiTheme: Boolean) : MainActivityUiState
}