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
package com.example.hnotes.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.model.Label
import com.example.hnotes.core.model.Note

@Composable
fun LabelCard(label: Label, isSelected: Boolean, onToggle: () -> Unit) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onToggle()
                },
                role = Role.Checkbox,
            ),
        horizontalArrangement =
        Arrangement.spacedBy(
            16.dp,
            Alignment.Start,
        ),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggle() },
            )

            Text(
                text = label.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier =
                Modifier
                    .weight(weight = 1f),
            )
        },
    )
}

@PreviewLightDark
@Composable
fun LabelCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    AppTheme {
        LabelCard(
            label = notes[true]!![1].labels.first(),
            isSelected = true,
            onToggle = {},
        )
    }
}
