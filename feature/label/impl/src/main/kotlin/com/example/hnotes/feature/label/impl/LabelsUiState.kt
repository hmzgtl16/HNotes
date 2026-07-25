package com.example.hnotes.feature.label.impl

import com.example.hnotes.core.model.Label

sealed interface LabelsUiState {
    data object Loading : LabelsUiState
    data class Success(
        val allLabels: List<Label>,
        val selectedLabels: List<Label> = emptyList()
    ) : LabelsUiState
}