package com.example.hnotes.core.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import com.example.hnotes.core.designsystem.component.HNotesAnimatedStrikethroughText
import com.example.hnotes.core.designsystem.component.ThemePreviews
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.designsystem.theme.LocalTintTheme
import com.example.hnotes.core.model.data.RepeatMode
import com.example.hnotes.core.model.data.Task
import com.example.hnotes.core.model.data.isExpired

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskCard(
    task: Task,
    multiSelectionEnabled: Boolean,
    enableMultiSelection: () -> Unit,
    selected: Boolean,
    onSelectedChanged: (Task) -> Unit,
    onTaskClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .combinedClickable(
                onClick = {
                    if (multiSelectionEnabled) onSelectedChanged(task)
                    else onTaskClick(task.id)
                },
                onLongClick = {
                    if (multiSelectionEnabled) return@combinedClickable
                    enableMultiSelection()
                    onSelectedChanged(task)
                },
                role = Role.RadioButton
            )
            .semantics { onClick(label = "Open Task", action = null) },
        content = {

            CompositionLocalProvider(
                value = LocalContentColor provides
                        if (task.isCompleted) LocalContentColor.current.copy(alpha = 0.38f)
                        else LocalContentColor.current,
                content = {
                    ListItem(
                        headlineContent = {
                            HNotesAnimatedStrikethroughText(
                                modifier = Modifier.fillMaxWidth(),
                                text = task.title.ifEmpty { stringResource(id = R.string.core_ui_task_untitled) },
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.titleMedium,
                                visible = task.isCompleted,
                            )
                        },
                        supportingContent = {
                            HNotesAnimatedStrikethroughText(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp),
                                text = task.reminder.format(),
                                color = if (task.isExpired() && !multiSelectionEnabled)
                                    MaterialTheme.colorScheme.error
                                else LocalContentColor.current,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.labelSmall,
                                visible = task.isCompleted,
                            )
                        },
                        leadingContent = {
                            if (!multiSelectionEnabled && task.isCompleted) {
                                Icon(
                                    imageVector = HNotesIcons.Check,
                                    contentDescription = null
                                )
                            }
                        },
                        trailingContent = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(
                                    space = 0.dp,
                                    alignment = Alignment.CenterVertically
                                ),
                                content = {

                                    if (!multiSelectionEnabled) {
                                        Icon(
                                            imageVector = if (task.repeatMode == RepeatMode.NONE) HNotesIcons.AlarmOnce
                                            else HNotesIcons.AlarmRepeat,
                                            contentDescription = null,
                                            modifier = Modifier.minimumInteractiveComponentSize()
                                        )

                                        Text(
                                            text = stringResource(id = task.repeatMode.id()),
                                            modifier = Modifier,
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
                        },
                        colors = ListItemDefaults.colors(
                            containerColor = Color.Transparent,
                            headlineColor = LocalContentColor.current,
                            supportingColor = LocalContentColor.current,
                            leadingIconColor = LocalContentColor.current,
                            trailingIconColor = LocalContentColor.current
                        )
                    )
                }
            )
        }
    )
}


@Composable
fun TaskSearchCard(
    task: Task,
    onTaskClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {

    ElevatedCard(
        onClick = { onTaskClick(task.id) },
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .semantics { onClick(label = "Open Task", action = null) },
        content = {

            ListItem(
                headlineContent = {
                    Text(
                        text = task.title.ifEmpty { stringResource(id = R.string.core_ui_task_untitled) },
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                supportingContent = {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        text = task.reminder.format(),
                        color = if (task.isExpired()) MaterialTheme.colorScheme.error
                        else LocalContentColor.current,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.labelSmall,
                    )
                },
                leadingContent = {
                    Box(
                        modifier = Modifier
                            .toggleable(
                                value = task.isCompleted,
                                role = Role.Checkbox,
                                onValueChange = { }
                            ),
                        contentAlignment = Alignment.Center,
                        content = {
                            Checkbox(
                                checked = task.isCompleted,
                                onCheckedChange = null
                            )
                        }
                    )
                },
                trailingContent = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween,
                        content = {

                            Icon(
                                imageVector = if (task.repeatMode == RepeatMode.NONE)
                                    HNotesIcons.AlarmOnce else HNotesIcons.AlarmRepeat,
                                contentDescription = null,
                                tint = if (task.isExpired()) MaterialTheme.colorScheme.error
                                else LocalContentColor.current,
                                modifier = Modifier.minimumInteractiveComponentSize()
                            )

                            Text(
                                text = stringResource(id = task.repeatMode.id()),
                                color = if (task.isExpired()) MaterialTheme.colorScheme.error
                                else Color.Unspecified,
                                modifier = Modifier,
                            )
                        }
                    )
                }
            )
        }
    )
}

@ThemePreviews
@Composable
fun TaskCardPreview(
    @PreviewParameter(TasksPreviewParameterProvider::class)
    tasks: Map<Boolean, List<Task>>,
) {
    HNotesTheme {
        TaskCard(
            task = tasks[false]!![1],
            multiSelectionEnabled = false,
            enableMultiSelection = {},
            selected = false,
            onSelectedChanged = {},
            onTaskClick = {}
        )
    }
}

@ThemePreviews
@Composable
fun TaskCardCompletePreview(
    @PreviewParameter(TasksPreviewParameterProvider::class)
    tasks: Map<Boolean, List<Task>>,
) {
    HNotesTheme {
        TaskCard(
            task = tasks[true]!![1],
            multiSelectionEnabled = false,
            enableMultiSelection = {},
            selected = false,
            onSelectedChanged = {},
            onTaskClick = {}
        )
    }
}

@ThemePreviews
@Composable
fun TaskCardSelectedPreview(
    @PreviewParameter(TasksPreviewParameterProvider::class)
    tasks: Map<Boolean, List<Task>>,
) {
    HNotesTheme {
        TaskCard(
            task = tasks[false]!![1],
            multiSelectionEnabled = true,
            enableMultiSelection = {},
            selected = true,
            onSelectedChanged = {},
            onTaskClick = {}
        )
    }
}

@ThemePreviews
@Composable
fun TaskCardExpiredPreview(
    @PreviewParameter(TasksPreviewParameterProvider::class)
    tasks: Map<Boolean, List<Task>>,
) {
    HNotesTheme {
        TaskCard(
            task = tasks[false]!![0],
            multiSelectionEnabled = false,
            enableMultiSelection = {},
            selected = false,
            onSelectedChanged = {},
            onTaskClick = {}
        )
    }
}
