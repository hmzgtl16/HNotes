/*
 * Copyright (c) 2026 GATTAL Hamza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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
class LabelViewModel
@AssistedInject
constructor(
    private val navigator: Navigator,
    private val labelRepository: LabelRepository,
    @Assisted private val navKey: LabelNavKey,
) : ViewModel() {
    val uiState: StateFlow<LabelsUiState> =
        labelRepository
            .getAllLabels()
            .combine(
                labelRepository.getLabelsForNote(navKey.noteId),
            ) { allLabels, selectedLabels ->
                LabelsUiState.Success(
                    allLabels = allLabels,
                    selectedLabels = selectedLabels,
                )
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000L),
                initialValue = LabelsUiState.Loading,
            )

    fun onEvent(event: LabelsDialogEvent) {
        when (event) {
            is LabelsDialogEvent.Dismiss -> navigateBack()
            is LabelsDialogEvent.ToggleLabelSelection -> toggleLabelSelection(event.label)
        }
    }

    private fun toggleLabelSelection(label: Label) =
        viewModelScope.launch {
            val currentNoteId = navKey.noteId
            val isSelected =
                (uiState.value as? LabelsUiState.Success)
                    ?.selectedLabels?.any { it.id == label.id } ?: false

            if (isSelected) {
                labelRepository.unlinkLabelFromNote(currentNoteId, label.id)
            } else {
                labelRepository.linkLabelToNote(currentNoteId, label.id)
            }
        }

    private fun navigateBack() =
        viewModelScope.launch {
            navigator.navigateBack()
        }

    @AssistedFactory
    interface Factory {
        fun create(navKey: LabelNavKey): LabelViewModel
    }
}
