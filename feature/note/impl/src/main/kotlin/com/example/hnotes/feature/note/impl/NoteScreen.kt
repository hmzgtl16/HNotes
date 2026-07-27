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
package com.example.hnotes.feature.note.impl

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnotes.core.design.component.AppAlertDialog
import com.example.hnotes.core.design.component.AppBackground
import com.example.hnotes.core.design.component.AppBottomAppBar
import com.example.hnotes.core.design.component.AppIconButton
import com.example.hnotes.core.design.component.AppOutlinedTextField
import com.example.hnotes.core.design.component.AppTextButton
import com.example.hnotes.core.design.component.ThemePreviews
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Reminder
import com.example.hnotes.core.model.RepeatMode
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.EditableItemCard
import com.example.hnotes.core.ui.PaletteModalBottomSheet
import com.example.hnotes.core.ui.ReminderCard
import com.example.hnotes.core.ui.ReminderDateTimePickerDialog
import com.example.hnotes.core.ui.formatter
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Duration.Companion.hours

@Composable
internal fun NoteScreen(modifier: Modifier = Modifier, viewModel: NoteViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler(
        onBack = {
            viewModel.onEvent(event = NoteScreenEvent.SaveNote)
        },
    )

    NoteScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteScreen(uiState: NoteUiState, onEvent: (NoteScreenEvent) -> Unit, modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current
    val paletteModalBottomSheetState = rememberModalBottomSheetState()
    val formattedLastEdit =
        remember(uiState.note?.updated) {
            uiState.note?.updated
                ?.toLocalDateTime(timeZone = TimeZone.currentSystemDefault())
                ?.format(format = formatter)
        }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor =
        uiState.backgroundColor
            ?.let(::Color) ?: MaterialTheme.colorScheme.background,
        bottomBar = {
            AppBottomAppBar(
                actions = {
                    AppIconButton(
                        onClick = { onEvent(NoteScreenEvent.Undo) },
                        enabled = uiState.canUndo,
                        icon = {
                            Icon(
                                imageVector = AppIcons.Undo,
                                contentDescription = null,
                            )
                        },
                    )

                    AppIconButton(
                        onClick = { onEvent(NoteScreenEvent.Redo) },
                        enabled = uiState.canRedo,
                        icon = {
                            Icon(
                                imageVector = AppIcons.Redo,
                                contentDescription = null,
                            )
                        },
                    )

                    AppIconButton(
                        onClick = { onEvent(NoteScreenEvent.AddItem) },
                        icon = {
                            Icon(
                                imageVector = AppIcons.Checked,
                                contentDescription = null,
                            )
                        },
                    )

                    AppIconButton(
                        onClick = { onEvent(NoteScreenEvent.ReminderPickerVisibilityChanged(true)) },
                        icon = {
                            Icon(
                                imageVector = AppIcons.Reminder,
                                contentDescription = null,
                            )
                        },
                    )

                    AppIconButton(
                        onClick = { onEvent(NoteScreenEvent.PaletteVisibilityChanged(true)) },
                        icon = {
                            Icon(
                                imageVector = AppIcons.Palette,
                                contentDescription = null,
                            )
                        },
                    )

                    AppIconButton(
                        onClick = {
                            onEvent(NoteScreenEvent.NavigateToLabel)
                        },
                        icon = {
                            Icon(
                                imageVector = AppIcons.Label,
                                contentDescription = null,
                            )
                        },
                    )

                    AppIconButton(
                        onClick = { onEvent(NoteScreenEvent.CopyNote) },
                        icon = {
                            Icon(
                                imageVector = AppIcons.Copy,
                                contentDescription = null,
                            )
                        },
                    )

                    AppIconButton(
                        onClick = { onEvent(NoteScreenEvent.DeleteDialogVisibilityChanged(true)) },
                        icon = {
                            Icon(
                                imageVector = AppIcons.Delete,
                                contentDescription = null,
                            )
                        },
                    )
                },
                containerColor = uiState.backgroundColor?.let(::Color) ?: Color.Transparent,
            )
        },
        content = { padding ->

            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues = padding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =
                Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.Top,
                ),
                content = {
                    Column(
                        modifier =
                        Modifier
                            .fillMaxWidth()
                            .weight(weight = 1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement =
                        Arrangement.spacedBy(
                            space = 16.dp,
                            alignment = Alignment.Top,
                        ),
                        content = {
                            AppOutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = uiState.title,
                                onValueChange = { onEvent(NoteScreenEvent.TitleChanged(it)) },
                                placeholder = {
                                    Text(text = stringResource(id = R.string.feature_note_title_placeholder))
                                },
                                keyboardOptions =
                                KeyboardOptions.Default.copy(
                                    capitalization = KeyboardCapitalization.Words,
                                    autoCorrectEnabled = true,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next,
                                ),
                                keyboardActions =
                                KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(focusDirection = FocusDirection.Down)
                                    },
                                ),
                            )

                            AppOutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = uiState.content,
                                onValueChange = { onEvent(NoteScreenEvent.ContentChanged(it)) },
                                placeholder = {
                                    Text(text = stringResource(id = R.string.feature_note_description_placeholder))
                                },
                                keyboardOptions =
                                KeyboardOptions.Default.copy(
                                    capitalization = KeyboardCapitalization.Words,
                                    autoCorrectEnabled = true,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done,
                                ),
                            )

                            uiState.reminder?.let {
                                ReminderCard(
                                    reminder = it,
                                    modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                )
                            }

                            if (uiState.labels.isNotEmpty()) {
                                FlowRow(
                                    modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    content = {
                                        uiState.labels.forEach { label ->
                                            AssistChip(
                                                onClick = {
                                                    onEvent(NoteScreenEvent.NavigateToLabel)
                                                },
                                                label = {
                                                    Text(text = label.name)
                                                },
                                                colors =
                                                AssistChipDefaults.assistChipColors(
                                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                                ),
                                            )
                                        }
                                    },
                                )
                            }

                            Column(
                                content = {
                                    uiState.items.forEachIndexed { index, item ->
                                        EditableItemCard(
                                            item = item,
                                            onItemChanged = {
                                                onEvent(
                                                    NoteScreenEvent.UpdateItem(
                                                        index = index,
                                                        item = it,
                                                    ),
                                                )
                                            },
                                            onDeleteItemClick = {
                                                onEvent(
                                                    NoteScreenEvent.RemoveItem(index = index),
                                                )
                                            },
                                            modifier = Modifier,
                                        )
                                    }
                                },
                            )
                        },
                    )

                    uiState.note?.updated?.let {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text =
                            stringResource(
                                id = R.string.feature_note_last_edit,
                                formattedLastEdit!!,
                            ),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall,
                        )
                    }
                },
            )
        },
    )

    if (uiState.isReminderPickerVisible) {
        ReminderDateTimePickerDialog(
            reminder = uiState.reminder,
            onConfirmClick = {
                onEvent(NoteScreenEvent.ReminderChanged(it))
                onEvent(NoteScreenEvent.ReminderPickerVisibilityChanged(false))
            },
            onCancelClick = { onEvent(NoteScreenEvent.ReminderPickerVisibilityChanged(false)) },
            onDeleteClick = {
                onEvent(NoteScreenEvent.ReminderChanged(null))
                onEvent(NoteScreenEvent.ReminderPickerVisibilityChanged(false))
            },
        )
    }

    if (uiState.isPaletteVisible) {
        PaletteModalBottomSheet(
            sheetState = paletteModalBottomSheetState,
            onDismissRequest = { onEvent(NoteScreenEvent.PaletteVisibilityChanged(false)) },
            backgroundColor = uiState.backgroundColor,
            onBackgroundColorChange = {
                onEvent(NoteScreenEvent.BackgroundColorChanged(it))
                onEvent(NoteScreenEvent.PaletteVisibilityChanged(false))
            },
        )
    }

    if (uiState.isDeleteDialogVisible) {
        AppAlertDialog(
            onDismissRequest = {
                onEvent(NoteScreenEvent.DeleteDialogVisibilityChanged(false))
            },
            confirmButton = {
                AppTextButton(
                    onClick = {
                        onEvent(NoteScreenEvent.DeleteNote)
                        onEvent(NoteScreenEvent.DeleteDialogVisibilityChanged(false))
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.feature_note_delete_note_confirm),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                )
            },
            dismissButton = {
                AppTextButton(
                    onClick = { onEvent(NoteScreenEvent.DeleteDialogVisibilityChanged(false)) },
                    text = {
                        Text(
                            text = stringResource(R.string.feature_note_delete_note_dismiss),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                )
            },
            title = {
                Text(
                    text =
                    stringResource(
                        id = R.string.feature_note_delete_note_title,
                        uiState.title.ifEmpty {
                            stringResource(id = R.string.feature_note_title_unspecified)
                        },
                    ),
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.feature_note_delete_note_description),
                    style = MaterialTheme.typography.bodyMedium,
                )
            },
        )
    }
}

@ThemePreviews
@DevicePreviews
@Composable
internal fun NoteScreenPreview() {
    AppTheme {
        AppBackground {
            NoteScreen(
                uiState =
                NoteUiState(
                    title = "Note Title",
                    content = "Note description for...",
                    backgroundColor = null,
                    reminder =
                    Reminder(
                        time = Clock.System.now().plus(4.hours),
                        repeatMode = RepeatMode.DAILY,
                    ),
                    items =
                    listOf(
                        Item(id = 1L, content = "Item 1", checked = false),
                        Item(id = 2L, content = "Item 2", checked = true),
                        Item(id = 3L, content = "Item 3", checked = false),
                        Item(id = 4L, content = "Item 4", checked = false),
                        Item(id = 5L, content = "Item 5", checked = true),
                    ),
                ),
                onEvent = {},
            )
        }
    }
}
