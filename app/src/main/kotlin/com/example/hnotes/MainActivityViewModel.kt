package com.example.hnotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.LabelRepository
import com.example.hnotes.core.data.repository.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    userDataRepository: UserDataRepository,
    labelRepository: LabelRepository
) : ViewModel() {
    val uiState: StateFlow<MainActivityUiState> = combine(
        userDataRepository.userData,
        labelRepository.getAllLabels()
    ) { userData, labels ->
        MainActivityUiState.Success(
            theme = userData.theme,
            useDynamicColor = userData.useDynamicColor,
            labels = labels
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainActivityUiState.Loading,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
    )
}