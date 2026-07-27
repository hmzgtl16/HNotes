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
package com.example.hnotes.core.design.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.design.theme.AppTheme

@Composable
fun AppCheckbox(checked: Boolean, onCheckedChange: (Boolean) -> Unit, text: @Composable () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier =
        modifier
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Checkbox,
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Checkbox(
                checked = checked,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors(),
            )

            text()
        },
    )
}

@Composable
fun AppTriStateCheckbox(state: ToggleableState, onClick: () -> Unit, text: @Composable () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier =
        modifier
            .triStateToggleable(
                state = state,
                onClick = onClick,
                role = Role.Checkbox,
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            TriStateCheckbox(
                state = state,
                onClick = null,
                colors = CheckboxDefaults.colors(),
            )

            text()
        },
    )
}

@Composable
fun TriStateCheckbox(state: ToggleableState, onClick: Nothing?, colors: CheckboxColors) {
    TODO("Not yet implemented")
}

@ThemePreviews
@Composable
fun AppCheckboxPreview() {
    AppTheme {
        AppBackground(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            content = {
                AppCheckbox(
                    checked = true,
                    onCheckedChange = {},
                    text = {
                        Text(
                            text = stringResource(id = android.R.string.selectAll),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                )
            },
        )
    }
}

@ThemePreviews
@Composable
fun AppTriStateCheckboxPreview() {
    AppTheme {
        AppBackground(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            content = {
                AppTriStateCheckbox(
                    state = ToggleableState.Indeterminate,
                    onClick = {},
                    text = {
                        Text(
                            text = stringResource(id = android.R.string.selectAll),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                )
            },
        )
    }
}
