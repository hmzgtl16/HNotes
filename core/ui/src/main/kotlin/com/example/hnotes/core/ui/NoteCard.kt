package com.example.hnotes.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.onClick
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.designsystem.component.HNotesIconToggleButton
import com.example.hnotes.core.designsystem.component.ThemePreviews
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.designsystem.theme.LocalTintTheme
import com.example.hnotes.core.model.data.Note

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    note: Note,
    multiSelectionEnabled: Boolean,
    enableMultiSelection: () -> Unit,
    selected: Boolean,
    onSelectedChanged: (Note) -> Unit,
    onNoteClick: (Long) -> Unit,
    onPinClick: (Note) -> Unit,
    modifier: Modifier = Modifier,
) {

    ElevatedCard(
        colors = if (note.backgroundColor != null)
            CardDefaults.elevatedCardColors(containerColor = Color(color = note.backgroundColor!!))
        else CardDefaults.elevatedCardColors(),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .combinedClickable(
                onClick = {
                    if (multiSelectionEnabled) onSelectedChanged(note)
                    else onNoteClick(note.id)
                },
                onLongClick = {
                    if (multiSelectionEnabled) return@combinedClickable
                    enableMultiSelection()
                    onSelectedChanged(note)
                },
                role = Role.RadioButton
            )
            .semantics { onClick(label = "Open Note", action = null) },
        content = {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 8.dp,
                    alignment = Alignment.CenterVertically
                ),
                content = {

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Start
                        ),
                        content = {
                            Text(
                                text = note.title.ifEmpty(
                                    defaultValue = { stringResource(id = R.string.core_ui_note_untitled) }
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.weight(weight = 1f)
                            )

                            if (!multiSelectionEnabled) {

                                HNotesIconToggleButton(
                                    checked = note.isPinned,
                                    onCheckedChange = { onPinClick(note) },
                                    icon = {
                                        Icon(
                                            imageVector = HNotesIcons.PinBorder,
                                            contentDescription = "Pinned"
                                        )
                                    },
                                    checkedIcon = {
                                        Icon(
                                            imageVector = HNotesIcons.Pin,
                                            contentDescription = "Unpinned"
                                        )
                                    }
                                )
                            }

                            if (multiSelectionEnabled) {

                                RadioButton(
                                    selected = selected,
                                    onClick = null,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.onPrimaryContainer,
                                        unselectedColor = LocalTintTheme.current.iconTint
                                    )
                                )
                            }
                        }
                    )

                    if (note.description.isNotEmpty()) {
                        Text(
                            text = note.description,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            )
        }
    )
}

@Composable
fun NoteCard(
    note: Note,
    onNoteClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {

    ElevatedCard(
        onClick = { onNoteClick(note.id) },
        colors = if (note.backgroundColor != null)
            CardDefaults.elevatedCardColors(containerColor = Color(color = note.backgroundColor!!))
        else CardDefaults.elevatedCardColors(),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .semantics { onClick(label = "Open Note", action = null) },
        content = {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(all = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.CenterVertically
                        ),
                        content = {

                            Text(
                                text = note.title.ifEmpty(
                                    defaultValue = { stringResource(id = R.string.core_ui_note_untitled) }
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.fillMaxWidth()
                            )

                            if (note.description.isNotEmpty()) {
                                Text(
                                    text = note.description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    )
                }
            )
        }
    )
}

@ThemePreviews
@Composable
fun NoteCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>,
) {
    HNotesTheme {
        NoteCard(
            note = notes[true]!![1],
            multiSelectionEnabled = true,
            enableMultiSelection = {},
            selected = true,
            onSelectedChanged = {},
            onNoteClick = {},
            onPinClick = {}
        )
    }
}
