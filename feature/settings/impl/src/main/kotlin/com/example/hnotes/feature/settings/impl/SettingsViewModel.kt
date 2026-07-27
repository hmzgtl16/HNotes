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
class SettingsViewModel
    @Inject
    constructor(private val navigator: Navigator, private val userDataRepository: UserDataRepository) : ViewModel() {
        val uiState: StateFlow<SettingsDialogState> =
            userDataRepository.userData
                .map {
                    SettingsDialogState.Success(
                        theme = it.theme,
                        useDynamicColor = it.useDynamicColor,
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

        fun updateTheme(theme: Theme) =
            viewModelScope.launch {
                userDataRepository.setTheme(theme = theme)
            }

        fun updateDynamicColor(useDynamicColor: Boolean) =
            viewModelScope.launch {
                userDataRepository.setDynamicColorPreference(useDynamicColor)
            }

        private fun navigateBack() =
            viewModelScope.launch {
                navigator.navigateBack()
            }
    }
