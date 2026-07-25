package com.example.hnotes.feature.label.impl

import com.example.hnotes.core.model.Label

sealed interface LabelsDialogEvent {
    data object Dismiss : LabelsDialogEvent
    data class ToggleLabelSelection(val label: Label) : LabelsDialogEvent
}