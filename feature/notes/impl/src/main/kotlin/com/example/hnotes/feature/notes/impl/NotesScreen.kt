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
package com.example.hnotes.feature.notes.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnotes.core.design.component.AppBackground
import com.example.hnotes.core.design.component.AppButton
import com.example.hnotes.core.design.component.AppIconButton
import com.example.hnotes.core.design.component.AppLoadingWheel
import com.example.hnotes.core.design.component.AppTopAppBar
import com.example.hnotes.core.design.component.AppTriStateCheckbox
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.model.Note
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.NoteCard
import com.example.hnotes.core.ui.NotesPreviewParameterProvider

@Composable
internal fun NotesScreen(modifier: Modifier = Modifier, viewModel: NotesViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NotesScreen(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@Composable
internal fun NotesScreen(uiState: NotesUiState, onEvent: (NotesScreenEvent) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            when (uiState.notesState) {
                NotesState.Loading -> {
                    NotesScreenLoading()
                }

                is NotesState.Success -> {
                    if (uiState.notesState.notes.isNotEmpty()) {
                        NotesScreenContent(
                            uiState = uiState,
                            onEvent = onEvent,
                            modifier = modifier,
                        )
                    } else {
                        NotesScreenEmpty(
                            onEvent = onEvent,
                            modifier = modifier,
                        )
                    }
                }
            }
        },
    )
}

@Composable
fun NotesScreenLoading(modifier: Modifier = Modifier) {
    AppLoadingWheel(
        modifier = modifier,
        contentDescription = stringResource(id = R.string.feature_notes_loading),
    )
}

@Composable
fun NotesScreenEmpty(onEvent: (NotesScreenEvent) -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier =
        modifier
            .fillMaxSize()
            .padding(all = 16.dp),
        verticalArrangement =
        Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically,
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            Text(
                text = stringResource(id = R.string.feature_notes_empty_error),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(id = R.string.feature_notes_empty_description),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )

            AppButton(
                onClick = { onEvent(NotesScreenEvent.NavigateToNote()) },
                text = {
                    Text(text = stringResource(id = R.string.feature_notes_add_note))
                },
                trailingIcon = {
                    Icon(
                        imageVector = AppIcons.AddNote,
                        contentDescription = "Add Note",
                        modifier = Modifier.size(size = ButtonDefaults.IconSize),
                    )
                },
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreenContent(uiState: NotesUiState, onEvent: (NotesScreenEvent) -> Unit, modifier: Modifier = Modifier) {
    val staggeredGridState = rememberLazyStaggeredGridState()

    val expandedFab by remember {
        derivedStateOf { staggeredGridState.firstVisibleItemIndex == 0 }
    }

    val allNotesSelected =
        remember(
            key1 = uiState.notesState,
            key2 = uiState.selectedNotes,
            calculation = { uiState.allNotesSelectedState },
        )

    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(
        key1 = uiState.showUndoDeleteSnackbar,
        key2 = uiState.recentlyDeletedNotes,
        block = {
            if (uiState.showUndoDeleteSnackbar && uiState.recentlyDeletedNotes.isNotEmpty()) {
                val count = uiState.recentlyDeletedNotes.size
                val message =
                    context.resources
                        .getQuantityString(R.plurals.feature_notes_deleted_notes, count, count)
                val actionLabel = context.getString(R.string.feature_notes_undo)

                val result =
                    snackbarHostState.showSnackbar(
                        message = message,
                        actionLabel = actionLabel,
                        duration = SnackbarDuration.Short,
                    )
                when (result) {
                    SnackbarResult.ActionPerformed -> {
                        onEvent(NotesScreenEvent.RestoreNotes)
                    }

                    SnackbarResult.Dismissed -> {
                        onEvent(NotesScreenEvent.UndoSnackbarDismissed)
                    }
                }
            }
        },
    )

    Scaffold(
        modifier = modifier,
        topBar = {
            if (uiState.isMultiSelectionEnabled) {
                AppTopAppBar(
                    title = {
                        Text(
                            text =
                            if (uiState.selectedNotes.isEmpty()) {
                                stringResource(id = R.string.feature_notes_no_selected_notes)
                            } else {
                                pluralStringResource(
                                    id = R.plurals.feature_notes_selected_notes,
                                    count = uiState.selectedNotes.size,
                                    uiState.selectedNotes.size,
                                )
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    },
                    navigationIcon = {
                        AppIconButton(
                            onClick = {
                                onEvent(NotesScreenEvent.MultiSelectionChanged(false))
                                onEvent(NotesScreenEvent.SelectAllNotesChecked(false))
                            },
                            icon = {
                                Icon(
                                    imageVector = AppIcons.Close,
                                    contentDescription = null,
                                )
                            },
                        )
                    },
                    actions = {
                        val mostPinned = uiState.selectedNotes.all(predicate = Note::pinned)

                        AppIconButton(
                            onClick = { onEvent(NotesScreenEvent.PinNotes) },
                            icon = {
                                Icon(
                                    imageVector = if (mostPinned) AppIcons.PinBorder else AppIcons.Pin,
                                    contentDescription = null,
                                )
                            },
                        )

                        AppIconButton(
                            onClick = { onEvent(NotesScreenEvent.DeleteNotes) },
                            icon = {
                                Icon(
                                    imageVector = AppIcons.Delete,
                                    contentDescription = null,
                                )
                            },
                        )
                    },
                    isCenterAligned = false,
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
                )
            }
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing),
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onEvent(NotesScreenEvent.NavigateToNote()) },
                expanded = expandedFab,
                icon = {
                    Icon(
                        imageVector = AppIcons.AddNote,
                        contentDescription = null,
                    )
                },
                text = {
                    Text(text = stringResource(id = R.string.feature_notes_add_note))
                },
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = Color.Transparent,
        content = { paddingValues ->

            Column(
                modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                content = {
                    if (uiState.isMultiSelectionEnabled) {
                        AppTriStateCheckbox(
                            state = allNotesSelected,
                            onClick = {
                                val targetState = allNotesSelected != ToggleableState.On
                                onEvent(NotesScreenEvent.SelectAllNotesChecked(targetState))
                            },
                            text = {
                                Text(
                                    text = stringResource(id = R.string.feature_notes_select_all),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            },
                            modifier = Modifier.wrapContentWidth(),
                        )
                    }

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(minSize = 250.dp),
                        contentPadding = PaddingValues(all = 16.dp),
                        verticalItemSpacing = 16.dp,
                        state = staggeredGridState,
                        content = {
                            (uiState.notesState as NotesState.Success).notes[true]?.let { notes ->
                                if (notes.isNotEmpty()) {
                                    item(
                                        span = StaggeredGridItemSpan.FullLine,
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.feature_notes_pinned_group),
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 8.dp)
                                                    .animateItem(),
                                            )
                                        },
                                    )
                                }

                                items(
                                    items = notes,
                                    key = Note::id,
                                    contentType = { "Note" },
                                    itemContent = {
                                        NoteCard(
                                            note = it,
                                            multiSelectionEnabled = uiState.isMultiSelectionEnabled,
                                            enableMultiSelection = {
                                                onEvent(
                                                    NotesScreenEvent.MultiSelectionChanged(
                                                        true,
                                                    ),
                                                )
                                            },
                                            selected = uiState.selectedNotes.contains(it),
                                            onSelectedChanged = {
                                                onEvent(
                                                    NotesScreenEvent.NoteSelectedChanged(
                                                        it,
                                                    ),
                                                )
                                            },
                                            onPinClick = { onEvent(NotesScreenEvent.PinNote(it)) },
                                            onNoteClick = {
                                                onEvent(
                                                    NotesScreenEvent.NavigateToNote(
                                                        it.id,
                                                    ),
                                                )
                                            },
                                            modifier =
                                            Modifier
                                                .padding(horizontal = 8.dp)
                                                .animateItem(),
                                        )
                                    },
                                )
                            }

                            uiState.notesState.notes[false]?.let { notes ->
                                if (notes.isNotEmpty() && uiState.notesState.notes[true]?.isNotEmpty() ?: false) {
                                    item(
                                        span = StaggeredGridItemSpan.FullLine,
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.feature_notes_unpinned_group),
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier =
                                                Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 8.dp)
                                                    .animateItem(),
                                            )
                                        },
                                    )
                                }

                                items(
                                    items = notes,
                                    key = Note::id,
                                    contentType = { "Note" },
                                    itemContent = {
                                        NoteCard(
                                            note = it,
                                            multiSelectionEnabled = uiState.isMultiSelectionEnabled,
                                            enableMultiSelection = {
                                                onEvent(
                                                    NotesScreenEvent.MultiSelectionChanged(
                                                        true,
                                                    ),
                                                )
                                            },
                                            selected = uiState.selectedNotes.contains(it),
                                            onSelectedChanged = {
                                                onEvent(
                                                    NotesScreenEvent.NoteSelectedChanged(
                                                        it,
                                                    ),
                                                )
                                            },
                                            onPinClick = { onEvent(NotesScreenEvent.PinNote(it)) },
                                            onNoteClick = {
                                                onEvent(
                                                    NotesScreenEvent.NavigateToNote(
                                                        it.id,
                                                    ),
                                                )
                                            },
                                            modifier =
                                            Modifier
                                                .padding(horizontal = 8.dp)
                                                .animateItem(),
                                        )
                                    },
                                )
                            }
                        },
                    )
                },
            )
        },
    )
}

@DevicePreviews
@Composable
fun NotesScreenLoadingPreview() {
    AppTheme {
        AppBackground {
            NotesScreen(
                uiState = NotesUiState(),
                onEvent = {},
            )
        }
    }
}

@DevicePreviews
@Composable
fun NotesScreenEmptyPreview() {
    AppTheme {
        AppBackground {
            NotesScreen(
                uiState = NotesUiState(notesState = NotesState.Success(notes = emptyMap())),
                onEvent = {},
            )
        }
    }
}

@DevicePreviews
@Composable
fun NotesScreenContentPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    AppTheme {
        AppBackground {
            NotesScreen(
                uiState = NotesUiState(notesState = NotesState.Success(notes = notes)),
                onEvent = {},
            )
        }
    }
}

@DevicePreviews
@Composable
fun NotesScreenContentMultiSelectionEnabledPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    AppTheme {
        AppBackground {
            NotesScreen(
                uiState =
                NotesUiState(
                    notesState = NotesState.Success(notes = notes),
                    isMultiSelectionEnabled = true,
                    selectedNotes = notes.values.flatten().filter(Note::pinned),
                ),
                onEvent = {},
            )
        }
    }
}
