package com.example.hnotes.feature.note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnotes.core.designsystem.component.HNotesAlertDialog
import com.example.hnotes.core.designsystem.component.HNotesBackground
import com.example.hnotes.core.designsystem.component.HNotesIconButton
import com.example.hnotes.core.designsystem.component.HNotesIconToggleButton
import com.example.hnotes.core.designsystem.component.HNotesModalBottomSheet
import com.example.hnotes.core.designsystem.component.HNotesOutlinedIconToggleButton
import com.example.hnotes.core.designsystem.component.HNotesTextButton
import com.example.hnotes.core.designsystem.component.HNotesOutlinedTextField
import com.example.hnotes.core.designsystem.component.HNotesTopAppBar
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.ui.BackgroundColorsPreviewParameterProvider
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.PaletteModalBottomSheet
import com.example.hnotes.core.ui.format
import kotlinx.datetime.Instant

@Composable
internal fun NoteRoute(
    modifier: Modifier = Modifier,
    viewModel: NoteViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {

    val note by viewModel.note.collectAsStateWithLifecycle()
    val title by viewModel.title.collectAsStateWithLifecycle()
    val description by viewModel.description.collectAsStateWithLifecycle()
    val backgroundColor by viewModel.backgroundColor.collectAsStateWithLifecycle()
    val pinned by viewModel.pinned.collectAsStateWithLifecycle()
    val paletteVisibility by viewModel.paletteVisibility.collectAsStateWithLifecycle()
    val deleteDialogVisibility by viewModel.deleteDialogVisibility.collectAsStateWithLifecycle()
    val isDeleted by viewModel.isDeleted.collectAsStateWithLifecycle()

    BackHandler(
        onBack = {
            viewModel.saveNote()
            navigateBack()
        }
    )

    LaunchedEffect(key1 = isDeleted) {
        if (note == null &&isDeleted) navigateBack()
    }

    NoteScreen(
        title = title,
        onTitleChange = viewModel::onTitleChange,
        description = description,
        onDescriptionChange = viewModel::onDescriptionChange,
        backgroundColor = backgroundColor,
        onBackgroundColorChange = viewModel::onBackgroundColorChange,
        pinned = pinned,
        onPinnedChange = viewModel::onPinnedChange,
        lastEdit = note?.updated,
        paletteVisibility = paletteVisibility,
        onPaletteVisibilityChange = viewModel::onPaletteVisibilityChange,
        deleteDialogVisibility = deleteDialogVisibility,
        onDeleteDialogVisibilityChange = viewModel::onDeleteDialogVisibilityChange,
        onCopyClick = viewModel::copyNote,
        onDeleteClick = viewModel::deleteNote,
        onBackClick = {
            viewModel.saveNote()
            navigateBack()
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NoteScreen(
    title: String,
    onTitleChange: (String) -> Unit,
    description: String,
    onDescriptionChange: (String) -> Unit,
    backgroundColor: Int?,
    onBackgroundColorChange: (color: Int?) -> Unit,
    pinned: Boolean,
    onPinnedChange: (Boolean) -> Unit,
    lastEdit: Instant?,
    paletteVisibility: Boolean,
    onPaletteVisibilityChange: (Boolean) -> Unit,
    deleteDialogVisibility: Boolean,
    onDeleteDialogVisibilityChange: (Boolean) -> Unit,
    onCopyClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val focusManager = LocalFocusManager.current

    val paletteModalBottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = if (backgroundColor != null) Color(color = backgroundColor)
        else MaterialTheme.colorScheme.background,
        topBar = {
            HNotesTopAppBar(
                title = {},
                navigationIcon = {
                    HNotesIconButton(
                        onClick = onBackClick,
                        icon = {
                            Icon(
                                imageVector = HNotesIcons.Back,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                            )
                        }
                    )
                },
                actions = {

                    HNotesIconToggleButton(
                        checked = pinned,
                        onCheckedChange = { onPinnedChange(it) },
                        icon = {
                            Icon(
                                imageVector = HNotesIcons.PinBorder,
                                contentDescription = null
                            )
                        },
                        checkedIcon = {
                            Icon(
                                imageVector = HNotesIcons.Pin,
                                contentDescription = null
                            )
                        }
                    )

                    HNotesIconButton(
                        onClick = { onPaletteVisibilityChange(true) },
                        icon = {
                            Icon(
                                imageVector = HNotesIcons.Palette,
                                contentDescription = null,
                            )
                        }
                    )

                    HNotesIconButton(
                        onClick = onCopyClick,
                        icon = {
                            Icon(
                                imageVector = HNotesIcons.Copy,
                                contentDescription = null,
                            )
                        }
                    )

                    HNotesIconButton(
                        onClick = { onDeleteDialogVisibilityChange(true) },
                        icon = {
                            Icon(
                                imageVector = HNotesIcons.Delete,
                                contentDescription = null,
                            )
                        }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = if (backgroundColor != null) Color(color = backgroundColor)
                    else Color.Transparent
                )
            )
        },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = it),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.Top
                ),
                content = {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(weight = 1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 16.dp,
                            alignment = Alignment.Top
                        ),
                        content = {

                            HNotesOutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = title,
                                onValueChange = onTitleChange,
                                placeholder = {
                                    Text(text = stringResource(id = R.string.feature_note_title_placeholder))
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    capitalization = KeyboardCapitalization.Words,
                                    autoCorrectEnabled = true,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(focusDirection = FocusDirection.Down)
                                    }
                                )
                            )

                            HNotesOutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = description,
                                onValueChange = onDescriptionChange,
                                placeholder = {
                                    Text(text = stringResource(id = R.string.feature_note_description_placeholder))
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    capitalization = KeyboardCapitalization.Words,
                                    autoCorrectEnabled = true,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Done
                                )
                            )
                        }
                    )

                    if (lastEdit != null)
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(
                                id = R.string.feature_note_last_edit,
                                lastEdit.format()
                            ),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelSmall,
                        )
                }
            )
        }
    )

    if (paletteVisibility) {
        PaletteModalBottomSheet(
            sheetState = paletteModalBottomSheetState,
            onDismissRequest = { onPaletteVisibilityChange(false) },
            backgroundColor = backgroundColor,
            onBackgroundColorChange = {
                onBackgroundColorChange(it)
                onPaletteVisibilityChange(false)
            }
        )
    }

    if (deleteDialogVisibility) {
        HNotesAlertDialog(
            onDismissRequest = {
                onDeleteDialogVisibilityChange(false)
            },
            confirmButton = {
                HNotesTextButton(
                    onClick = {
                        onDeleteDialogVisibilityChange(false)
                        onDeleteClick()
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.feature_note_delete_note_confirm),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            },
            dismissButton = {
                HNotesTextButton(
                    onClick = {
                        onDeleteDialogVisibilityChange(false)
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.feature_note_delete_note_dismiss),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                )
            },
            title = {
                Text(
                    text = stringResource(
                        id = R.string.feature_note_delete_note_title,
                        title.ifEmpty {
                            stringResource(id = R.string.feature_note_title_unspecified)
                        }
                    ),
                    style = MaterialTheme.typography.titleLarge,
                )
            },
            text = {
                Text(
                    text = stringResource(R.string.feature_note_delete_note_description),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        )
    }
}

@DevicePreviews
@Composable
fun NoteScreenPreview(
    @PreviewParameter(BackgroundColorsPreviewParameterProvider::class)
    backgroundColors: List<Color>,
) {
    HNotesTheme {
        HNotesBackground {
            NoteScreen(
                title = "",
                onTitleChange = {},
                description = "",
                onDescriptionChange = {},
                backgroundColor = null,
                onBackgroundColorChange = {},
                pinned = true,
                onPinnedChange = {},
                lastEdit = null,
                paletteVisibility = false,
                onPaletteVisibilityChange = {},
                deleteDialogVisibility = false,
                onDeleteDialogVisibilityChange = {},
                onCopyClick = {},
                onDeleteClick = {},
                onBackClick = {},
            )
        }
    }
}
