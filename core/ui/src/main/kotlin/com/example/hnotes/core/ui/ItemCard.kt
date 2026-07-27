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

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.design.component.AppBackground
import com.example.hnotes.core.design.component.AppIconButton
import com.example.hnotes.core.design.component.AppIconToggleButton
import com.example.hnotes.core.design.component.AppOutlinedTextField
import com.example.hnotes.core.design.component.ThemePreviews
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Note

@Composable
fun ItemCard(item: Item, modifier: Modifier = Modifier) {
    val transition =
        updateTransition(
            targetState = item.checked,
            label = "Check Transition",
        )
    val alpha by transition.animateFloat(label = "Alpha") {
        if (it) 0.5f else 1f
    }

    Row(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Start),
    ) {
        Icon(
            modifier =
            Modifier
                .size(size = 16.dp)
                .alpha(alpha = alpha),
            imageVector = if (item.checked) AppIcons.Checked else AppIcons.Unchecked,
            contentDescription = null,
        )

        Text(
            modifier = Modifier.alpha(alpha = alpha),
            text = item.content,
            style = MaterialTheme.typography.bodyLarge,
            textDecoration = if (item.checked) TextDecoration.LineThrough else null,
        )
    }
}

@Composable
fun EditableItemCard(item: Item, onItemChanged: (Item) -> Unit, onDeleteItemClick: () -> Unit, modifier: Modifier = Modifier) {
    var isFocused by remember { mutableStateOf(value = false) }

    val alpha by animateFloatAsState(
        targetValue = if (item.checked) 0.5f else 1f,
        label = "AlphaCheckTransition",
    )

    AppOutlinedTextField(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .onFocusChanged { isFocused = it.isFocused }
            .graphicsLayer { this.alpha = alpha },
        value = item.content,
        onValueChange = { onItemChanged(item.copy(content = it)) },
        leadingIcon = {
            AppIconToggleButton(
                checked = item.checked,
                onCheckedChange = { onItemChanged(item.copy(checked = it)) },
                colors =
                IconButtonDefaults.iconToggleButtonColors(
                    checkedContentColor = LocalContentColor.current,
                ),
                icon = {
                    Icon(
                        imageVector = AppIcons.Unchecked,
                        contentDescription = null,
                    )
                },
                checkedIcon = {
                    Icon(
                        imageVector = AppIcons.Checked,
                        contentDescription = null,
                    )
                },
            )
        },
        trailingIcon = {
            if (isFocused) {
                AppIconButton(
                    onClick = onDeleteItemClick,
                    icon = {
                        Icon(
                            imageVector = AppIcons.Close,
                            contentDescription = null,
                        )
                    },
                )
            }
        },
        textStyle =
        TextStyle.Default.copy(
            textDecoration = if (item.checked) TextDecoration.LineThrough else null,
        ),
    )
}

@ThemePreviews
@Composable
private fun CheckedItemCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    AppTheme {
        AppBackground(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            content = {
                ItemCard(item = notes[true]!![6].items.first())
            },
        )
    }
}

@ThemePreviews
@Composable
private fun UncheckedItemCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    AppTheme {
        AppBackground(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            content = {
                ItemCard(item = notes[true]!![6].items.last())
            },
        )
    }
}

@ThemePreviews
@Composable
private fun CheckedEditableItemCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    AppTheme {
        AppBackground(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            content = {
                EditableItemCard(
                    item = notes[true]!![6].items.first(),
                    onItemChanged = {},
                    onDeleteItemClick = {},
                )
            },
        )
    }
}

@ThemePreviews
@Composable
private fun UncheckedEditableItemCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    AppTheme {
        AppBackground(
            modifier =
            Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            content = {
                EditableItemCard(
                    item = notes[true]!![6].items.last(),
                    onItemChanged = {},
                    onDeleteItemClick = {},
                )
            },
        )
    }
}
