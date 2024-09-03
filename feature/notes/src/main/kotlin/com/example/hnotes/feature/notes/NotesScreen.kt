package com.example.hnotes.feature.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Button
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnotes.core.designsystem.component.HNotesBackground
import com.example.hnotes.core.designsystem.component.HNotesIconButton
import com.example.hnotes.core.designsystem.component.HNotesLoadingWheel
import com.example.hnotes.core.designsystem.component.HNotesTopAppBar
import com.example.hnotes.core.designsystem.component.HNotesTriStateCheckbox
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.model.data.Note
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.NoteCard
import com.example.hnotes.core.ui.NotesPreviewParameterProvider

@Composable
internal fun NotesRoute(
    navigateToNote: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NotesViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val selectedNotes by viewModel.selectedNotes.collectAsStateWithLifecycle()

    val deleteNotes by viewModel.deletedNotes.collectAsStateWithLifecycle()

    val isDeleteSuccess by viewModel.isDeleteSuccess.collectAsStateWithLifecycle()

    val multiSelectionEnabled by viewModel.multiSelectionEnabled.collectAsStateWithLifecycle()

    NotesScreen(
        uiState = uiState,
        selectedNotes = selectedNotes,
        onSelectAllNotesChecked = viewModel::onSelectAllNotesChecked,
        multiSelectionEnabled = multiSelectionEnabled,
        onMultiSelectionChanged = viewModel::onMultiSelectionChanged,
        noteSelected = selectedNotes::contains,
        onNoteSelectedChanged = viewModel::onNoteSelectedChanged,
        deleteNotes = viewModel::deleteNotes,
        pinNotes = viewModel::pinNotes,
        onNoteClick = navigateToNote,
        onPinClick = viewModel::pinNote,
        deletedNotes = deleteNotes,
        isDeleteSuccess = isDeleteSuccess,
        restoreNotes = viewModel::restoreNotes,
        modifier = modifier
    )
}

@Composable
internal fun NotesScreen(
    uiState: NotesUiState,
    selectedNotes: List<Note>,
    onSelectAllNotesChecked: (Boolean) -> Unit,
    multiSelectionEnabled: Boolean,
    onMultiSelectionChanged: (Boolean) -> Unit,
    noteSelected: (Note) -> Boolean,
    onNoteSelectedChanged: (Note) -> Unit,
    deleteNotes: () -> Unit,
    pinNotes: (Boolean) -> Unit,
    onNoteClick: (Long) -> Unit,
    onPinClick: (Note) -> Unit,
    deletedNotes: List<Note>,
    isDeleteSuccess: Boolean,
    restoreNotes: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            when (uiState) {
                NotesUiState.Loading -> {
                    NotesScreenLoading()
                }

                is NotesUiState.Success -> {
                    if (uiState.notes.isNotEmpty()) {
                        NotesScreenContent(
                            uiState = uiState,
                            selectedNotes = selectedNotes,
                            onAllNotesSelectedChanged = onSelectAllNotesChecked,
                            multiSelectionEnabled = multiSelectionEnabled,
                            onMultiSelectionChanged = onMultiSelectionChanged,
                            noteSelected = noteSelected,
                            onNoteSelectedChanged = onNoteSelectedChanged,
                            deleteNotes = deleteNotes,
                            pinNotes = pinNotes,
                            onNoteClick = onNoteClick,
                            onPinClick = onPinClick,
                            deletedNotes = deletedNotes,
                            isDeleteSuccess = isDeleteSuccess,
                            restoreNotes = restoreNotes,
                            modifier = modifier
                        )
                    } else {
                        NotesScreenEmpty(
                            onNoteClick = onNoteClick,
                            modifier = modifier
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun NotesScreenLoading(modifier: Modifier = Modifier) {
    HNotesLoadingWheel(
        modifier = modifier.testTag(tag = "notes::loading"),
        contentDescription = stringResource(id = R.string.feature_notes_loading)
    )
}

@Composable
fun NotesScreenEmpty(
    onNoteClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .testTag(tag = "notes::empty"),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
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

            Button(
                onClick = { onNoteClick(-1) },
                content = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            space = ButtonDefaults.IconSpacing,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                imageVector = HNotesIcons.AddNote,
                                contentDescription = "Add Note",
                                modifier = Modifier.size(size = ButtonDefaults.IconSize)
                            )

                            Text(text = stringResource(id = R.string.feature_notes_add_note))
                        }
                    )
                }
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreenContent(
    uiState: NotesUiState.Success,
    selectedNotes: List<Note>,
    onAllNotesSelectedChanged: (Boolean) -> Unit,
    multiSelectionEnabled: Boolean,
    onMultiSelectionChanged: (Boolean) -> Unit,
    noteSelected: (Note) -> Boolean,
    onNoteSelectedChanged: (Note) -> Unit,
    deleteNotes: () -> Unit,
    pinNotes: (Boolean) -> Unit,
    onNoteClick: (Long) -> Unit,
    onPinClick: (Note) -> Unit,
    deletedNotes: List<Note>,
    isDeleteSuccess: Boolean,
    restoreNotes: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current

    val staggeredGridState = rememberLazyStaggeredGridState()

    val snackbarHostState = remember { SnackbarHostState() }

    val expandedFab by remember {
        derivedStateOf { staggeredGridState.firstVisibleItemIndex == 0 }
    }

    var deleteNotesResult by remember { mutableStateOf(value = false) }

    LaunchedEffect(key1 = deleteNotesResult) {
        if (deleteNotesResult) restoreNotes()
    }
    
    LaunchedEffect(key1 = isDeleteSuccess) {
        if (isDeleteSuccess) {
            deleteNotesResult = snackbarHostState.showSnackbar(
                message = context.resources.getQuantityString(
                    R.plurals.feature_notes_removed_notes,
                    deletedNotes.size,
                    deletedNotes.size
                ),
                actionLabel = context.resources.getString(R.string.feature_notes_undo),
                duration = SnackbarDuration.Short
            ) == SnackbarResult.ActionPerformed
        }
    }

    val allNotesSelected = when {
        selectedNotes.isEmpty() -> ToggleableState.Off
        selectedNotes.containsAll(elements = uiState.notes.values.flatten()) -> ToggleableState.On
        else -> ToggleableState.Indeterminate
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (multiSelectionEnabled) {
                HNotesTopAppBar(
                    title = {
                        Text(
                            text = if (selectedNotes.isEmpty())
                                stringResource(id = R.string.feature_notes_no_selected_notes)
                            else {
                                pluralStringResource(
                                    id = R.plurals.feature_notes_selected_notes,
                                    count = selectedNotes.size,
                                    selectedNotes.size
                                )
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        HNotesIconButton(
                            onClick = {
                                onMultiSelectionChanged(false)
                                onAllNotesSelectedChanged(false)
                            },
                            icon = {
                                Icon(
                                    imageVector = HNotesIcons.Close,
                                    contentDescription = null
                                )
                            }
                        )
                    },
                    actions = {

                        val mostPinned = selectedNotes.all(predicate = Note::isPinned)

                        HNotesIconButton(
                            onClick = { pinNotes(!mostPinned) },
                            icon = {
                                Icon(
                                    imageVector = if(mostPinned) HNotesIcons.PinBorder else HNotesIcons.Pin,
                                    contentDescription = null
                                )
                            }
                        )

                        HNotesIconButton(
                            onClick = deleteNotes,
                            icon = {
                                Icon(
                                    imageVector = HNotesIcons.Delete,
                                    contentDescription = null
                                )
                            }
                        )
                    },
                    isCenterAligned = false,
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onNoteClick(-1) },
                expanded = expandedFab,
                icon = {
                    Icon(
                        imageVector = HNotesIcons.AddNote,
                        contentDescription = null
                    )
                },
                text = {

                    Text(text = stringResource(id = R.string.feature_notes_add_note))
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = Color.Transparent,
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .testTag(tag = "notes::content"),
                content = {

                    if (multiSelectionEnabled) {

                        HNotesTriStateCheckbox(
                            state = allNotesSelected,
                            onClick = {
                                if (allNotesSelected == ToggleableState.On)
                                    onAllNotesSelectedChanged(false)

                                if (allNotesSelected == ToggleableState.Off)
                                    onAllNotesSelectedChanged(true)

                                if (allNotesSelected == ToggleableState.Indeterminate)
                                    onAllNotesSelectedChanged(true)
                            },
                            text = {
                                Text(
                                    text = stringResource(id = R.string.feature_notes_select_all),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            },
                            modifier = Modifier.wrapContentWidth()
                        )
                    }

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(minSize = 250.dp),
                        contentPadding = PaddingValues(all = 16.dp),
                        verticalItemSpacing = 16.dp,
                        state = staggeredGridState,
                        content = {

                            uiState.notes.forEach { (isPinned, notes) ->

                                if (isPinned) {
                                    item(
                                        span = StaggeredGridItemSpan.FullLine,
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.feature_notes_pinned_group),
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 8.dp)
                                                    .animateItem()
                                            )
                                        }
                                    )
                                }

                                if (!isPinned && notes.isNotEmpty()) {
                                    item(
                                        span = StaggeredGridItemSpan.FullLine,
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.feature_notes_unpinned_group),
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 8.dp)
                                                    .animateItem()
                                            )
                                        }
                                    )
                                }

                                items(
                                    items = notes,
                                    key = { it.id },
                                    contentType = { "Note" },
                                    itemContent = {

                                        NoteCard(
                                            note = it,
                                            multiSelectionEnabled = multiSelectionEnabled,
                                            enableMultiSelection = { onMultiSelectionChanged(true) },
                                            selected = noteSelected(it),
                                            onSelectedChanged = onNoteSelectedChanged,
                                            onPinClick = onPinClick,
                                            onNoteClick = onNoteClick,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp)
                                                .animateItem()
                                        )
                                    }
                                )
                            }
                        }
                    )
                }
            )
        }
    )
}

@DevicePreviews
@Composable
fun NotesScreenLoadingPreview() {
    HNotesTheme {
        HNotesBackground {
            NotesScreen(
                uiState = NotesUiState.Loading,
                selectedNotes = emptyList(),
                onSelectAllNotesChecked = {},
                multiSelectionEnabled = false,
                onMultiSelectionChanged = {},
                noteSelected = { false },
                onNoteSelectedChanged = {},
                deleteNotes = {},
                pinNotes = {},
                onNoteClick = {},
                onPinClick = {},
                deletedNotes = emptyList(),
                isDeleteSuccess = false,
                restoreNotes = {}
            )
        }
    }
}

@DevicePreviews
@Composable
fun NotesScreenEmptyPreview() {
    HNotesTheme {
        HNotesBackground {
            NotesScreen(
                uiState = NotesUiState.Success(notes = emptyMap()),
                selectedNotes = emptyList(),
                onSelectAllNotesChecked = {},
                multiSelectionEnabled = false,
                onMultiSelectionChanged = {},
                noteSelected = { false },
                onNoteSelectedChanged = {},
                deleteNotes = {},
                pinNotes = {},
                onNoteClick = {},
                onPinClick = {},
                deletedNotes = emptyList(),
                isDeleteSuccess = false,
                restoreNotes = {}
            )
        }
    }
}

@DevicePreviews
@Composable
fun NotesScreenContentPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>
) {
    HNotesTheme {
        HNotesBackground {
            NotesScreen(
                uiState = NotesUiState.Success(notes = notes),
                selectedNotes = emptyList(),
                onSelectAllNotesChecked = {},
                multiSelectionEnabled = false,
                onMultiSelectionChanged = {},
                noteSelected = { false },
                onNoteSelectedChanged = {},
                deleteNotes = {},
                pinNotes = {},
                onNoteClick = {},
                onPinClick = {},
                deletedNotes = emptyList(),
                isDeleteSuccess = false,
                restoreNotes = {}
            )
        }
    }
}

@DevicePreviews
@Composable
fun NotesScreenContentMultiSelectionEnabledPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>
) {
    HNotesTheme {
        HNotesBackground {
            NotesScreen(
                uiState = NotesUiState.Success(notes = notes),
                selectedNotes = notes.values.flatten().filter(Note::isPinned),
                onSelectAllNotesChecked = {},
                multiSelectionEnabled = true,
                onMultiSelectionChanged = {},
                noteSelected = { notes.values.flatten().filter(Note::isPinned).contains(it) },
                onNoteSelectedChanged = {},
                deleteNotes = {},
                pinNotes = {},
                onNoteClick = {},
                onPinClick = {},
                deletedNotes = emptyList(),
                isDeleteSuccess = false,
                restoreNotes = {}
            )
        }
    }
}
