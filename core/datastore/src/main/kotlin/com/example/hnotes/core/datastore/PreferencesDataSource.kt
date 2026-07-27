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
package com.example.hnotes.core.datastore

import androidx.datastore.core.DataStore
import com.example.hnotes.core.model.Theme
import com.example.hnotes.core.model.UserData
import jakarta.inject.Inject
import kotlinx.coroutines.flow.map

class PreferencesDataSource
    @Inject
    constructor(private val userPreferences: DataStore<UserPreferences>) {
        val userData =
            userPreferences.data
                .map {
                    UserData(
                        theme = it.uiThemeConfig.asTheme(),
                        useDynamicColor = it.useDynamicUiTheme,
                    )
                }

        suspend fun setTheme(theme: Theme) {
            userPreferences.updateData {
                it.copy {
                    uiThemeConfig = theme.asProto()
                }
            }
        }

        suspend fun setDynamicColor(useDynamicColor: Boolean) {
            userPreferences.updateData {
                it.copy { useDynamicUiTheme = useDynamicColor }
            }
        }
    }
