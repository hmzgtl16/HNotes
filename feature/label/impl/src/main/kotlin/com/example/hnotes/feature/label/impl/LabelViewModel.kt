package com.example.hnotes.feature.label.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.LabelRepository
import com.example.hnotes.core.model.Label
import com.example.hnotes.core.navigation.Navigator
import com.example.hnotes.feature.label.api.navigation.LabelNavKey
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = LabelViewModel.Factory::class)
class LabelViewModel @AssistedInject constructor(
    private val navigator: Navigator,
    private val labelRepository: LabelRepository,
    @Assisted private val navKey: LabelNavKey
) : ViewModel() {

    val uiState: StateFlow<LabelsUiState> = labelRepository
        .getAllLabels()
        .combine(
            labelRepository.getLabelsForNote(navKey.noteId)
        ) { allLabels, selectedLabels ->
            LabelsUiState.Success(
                allLabels = allLabels,
                selectedLabels = selectedLabels
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = LabelsUiState.Loading
        )

    fun onEvent(event: LabelsDialogEvent) {
        when (event) {
            is LabelsDialogEvent.Dismiss -> navigateBack()
            is LabelsDialogEvent.ToggleLabelSelection -> toggleLabelSelection(event.label)
        }
    }

    private fun toggleLabelSelection(label: Label) = viewModelScope.launch {
        val currentNoteId = navKey.noteId
        val isSelected = (uiState.value as? LabelsUiState.Success)
            ?.selectedLabels?.any { it.id == label.id } ?: false

        if (isSelected) {
            labelRepository.unlinkLabelFromNote(currentNoteId, label.id)
        } else {
            labelRepository.linkLabelToNote(currentNoteId, label.id)
        }
    }

    private fun navigateBack() = viewModelScope.launch {
        navigator.navigateBack()
    }

    @AssistedFactory
    interface Factory {
        fun create(navKey: LabelNavKey): LabelViewModel
    }
}
