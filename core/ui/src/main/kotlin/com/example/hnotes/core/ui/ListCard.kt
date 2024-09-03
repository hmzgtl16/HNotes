package com.example.hnotes.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.hnotes.core.model.data.Item
import com.example.hnotes.core.model.data.List
import kotlin.collections.List as KList


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListCard(
    list: List,
    multiSelectionEnabled: Boolean,
    enableMultiSelection: () -> Unit,
    selected: Boolean,
    onSelectedChanged: (List) -> Unit,
    onListClick: (Long) -> Unit,
    onPinClick: (List) -> Unit,
    modifier: Modifier = Modifier
) {

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .combinedClickable(
                onClick = {
                    if (multiSelectionEnabled) onSelectedChanged(list)
                    else onListClick(list.id)
                },
                onLongClick = {
                    if (multiSelectionEnabled) return@combinedClickable
                    enableMultiSelection()
                    onSelectedChanged(list)
                },
                role = Role.RadioButton
            )
            .semantics { onClick(label = "Open List", action = null) },
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
                                text = list.title.ifEmpty(
                                    defaultValue = { stringResource(id = R.string.core_ui_list_untitled) }
                                ),
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Start,
                                modifier = Modifier.weight(weight = 1f)
                            )

                            if (!multiSelectionEnabled) {

                                HNotesIconToggleButton(
                                    checked = list.isPinned,
                                    onCheckedChange = { onPinClick(list) },
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

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Top
                        ),
                        content = {

                            list.items
                                .groupBy(Item::isCompleted)
                                .toSortedMap()
                                .forEach { ( isCompleted, items) ->

                                    if (isCompleted) {

                                        HorizontalDivider()
                                    }

                                    items.forEach {

                                        CompositionLocalProvider(
                                            value = LocalContentColor provides
                                                    if (it.isCompleted) LocalContentColor.current.copy(alpha = 0.38f)
                                                    else LocalContentColor.current,
                                            content = {
                                                Row(
                                                    Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.spacedBy(
                                                        space = 16.dp,
                                                        alignment = Alignment.Start
                                                    ),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    content = {

                                                        Icon(
                                                            imageVector = if (it.isCompleted) HNotesIcons.Checked
                                                            else HNotesIcons.Unchecked,
                                                            contentDescription = null
                                                        )

                                                        Text(
                                                            text = it.title,
                                                            style = MaterialTheme.typography.bodyLarge,
                                                        )
                                                    }
                                                )
                                            }
                                        )
                                    }
                                }
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun ListSearchCard(
    list: List,
    onListClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = { onListClick(list.id) },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .semantics { onClick(label = "Open List", action = null) },
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

                    Text(
                        text = list.title.ifEmpty(
                            defaultValue = { stringResource(id = R.string.core_ui_list_untitled) }
                        ),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 8.dp,
                            alignment = Alignment.Top
                        ),
                        content = {

                            list.items
                                .groupBy(Item::isCompleted)
                                .toSortedMap()
                                .forEach { ( isCompleted, items) ->

                                    if (isCompleted) {

                                        HorizontalDivider()
                                    }

                                    items.forEach {

                                        CompositionLocalProvider(
                                            value = LocalContentColor provides
                                                    if (it.isCompleted) LocalContentColor.current.copy(alpha = 0.38f)
                                                    else LocalContentColor.current,
                                            content = {
                                                Row(
                                                    Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.spacedBy(
                                                        space = 16.dp,
                                                        alignment = Alignment.Start
                                                    ),
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    content = {

                                                        Icon(
                                                            imageVector = if (it.isCompleted) HNotesIcons.Checked
                                                            else HNotesIcons.Unchecked,
                                                            contentDescription = null
                                                        )

                                                        Text(
                                                            text = it.title,
                                                            style = MaterialTheme.typography.bodyLarge,
                                                        )
                                                    }
                                                )
                                            }
                                        )
                                    }
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
fun ListCardPreview(
    @PreviewParameter(ListsPreviewParameterProvider::class)
    lists: Map<Boolean, KList<List>>,
) {
    HNotesTheme {
        ListCard(
            list = lists[true]!![0],
            multiSelectionEnabled = false,
            enableMultiSelection = {},
            selected = false,
            onSelectedChanged = {},
            onListClick = {},
            onPinClick = {}
        )
    }
}

@ThemePreviews
@Composable
fun ListCardSelectedPreview(
    @PreviewParameter(ListsPreviewParameterProvider::class)
    lists: Map<Boolean, KList<List>>,
) {
    HNotesTheme {
        ListCard(
            list = lists[true]!![0],
            multiSelectionEnabled = true,
            enableMultiSelection = {},
            selected = true,
            onSelectedChanged = {},
            onListClick = {},
            onPinClick = {}
        )
    }
}

@ThemePreviews
@Composable
fun ListSearchCardPreview(
    @PreviewParameter(ListsPreviewParameterProvider::class)
    lists: Map<Boolean, KList<List>>,
) {
    HNotesTheme {
        ListSearchCard(
            list = lists[true]!![0],
            onListClick = {}
        )
    }
}