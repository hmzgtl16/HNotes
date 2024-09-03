package com.example.hnotes.feature.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.commandiron.wheel_picker_compose.WheelDateTimePicker
import com.example.hnotes.core.designsystem.component.HNotesAlertDialog
import com.example.hnotes.core.designsystem.component.HNotesBackground
import com.example.hnotes.core.designsystem.component.HNotesDropdownMenuItem
import com.example.hnotes.core.designsystem.component.HNotesExposedDropdownMenu
import com.example.hnotes.core.designsystem.component.HNotesExposedDropdownMenuBox
import com.example.hnotes.core.designsystem.component.HNotesExposedDropdownMenuTextField
import com.example.hnotes.core.designsystem.component.HNotesFilledTonalButton
import com.example.hnotes.core.designsystem.component.HNotesOutlinedTextField
import com.example.hnotes.core.designsystem.component.HNotesTextButton
import com.example.hnotes.core.designsystem.component.ThemePreviews
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.designsystem.theme.LocalBackgroundTheme
import com.example.hnotes.core.model.data.RepeatMode
import com.example.hnotes.core.model.data.isExpired
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.format
import com.example.hnotes.core.ui.id
import com.example.hnotes.core.ui.toJavaLocalDateTime
import com.example.hnotes.core.ui.toKotlinInstant
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import java.time.LocalDateTime

@Composable
internal fun TaskRoute(
    modifier: Modifier = Modifier,
    viewModel: TaskViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {

    val task by viewModel.task.collectAsStateWithLifecycle()
    val title by viewModel.title.collectAsStateWithLifecycle()
    val reminder by viewModel.reminder.collectAsStateWithLifecycle()
    val repeatMode by viewModel.repeatMode.collectAsStateWithLifecycle()
    val reminderPickerVisibility by viewModel.reminderPickerVisibility.collectAsStateWithLifecycle()
    val isEdited by viewModel.isEdited.collectAsStateWithLifecycle()

    val doneEnabled by remember {
        derivedStateOf {
            if (title.isEmpty() || title.isBlank()) return@derivedStateOf false
            if (reminder == null) return@derivedStateOf false
            if (!isEdited) return@derivedStateOf false

            true
        }
    }

    val completeEnabled by remember {
        derivedStateOf {
            task != null && !task!!.isCompleted && task!!.isExpired()
        }
    }

    TaskDialog(
        title = title,
        onTitleChange = viewModel::onTitleChange,
        reminder = reminder,
        onReminderChange = viewModel::onReminderChange,
        onClearReminder = viewModel::onClearReminder,
        repeatMode = repeatMode,
        onRepeatModeChange = viewModel::onRepeatModeChange,
        reminderPickerVisibility = reminderPickerVisibility,
        onReminderPickerVisibilityChange = viewModel::onReminderPickerVisibilityChange,
        doneEnabled = doneEnabled,
        onDoneClick = {
            viewModel.saveTask()
            navigateBack()
        },
        completeEnabled = completeEnabled,
        onCompleteClick = {
            viewModel.completeTask()
            navigateBack()
        },
        modifier = modifier
    )
}

@Composable
internal fun TaskDialog(
    title: String,
    onTitleChange: (String) -> Unit,
    reminder: Instant?,
    onReminderChange: (Instant) -> Unit,
    onClearReminder: () -> Unit,
    repeatMode: RepeatMode,
    onRepeatModeChange: (RepeatMode) -> Unit,
    reminderPickerVisibility: Boolean,
    onReminderPickerVisibilityChange: (Boolean) -> Unit,
    doneEnabled: Boolean,
    onDoneClick: () -> Unit,
    completeEnabled: Boolean,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Box(
        modifier = modifier
            .wrapContentHeight()
            .background(
                color = LocalBackgroundTheme.current.color,
                shape = RoundedCornerShape(size = 20.dp)
            ),
        contentAlignment = Alignment.Center,
        content = {

            Column(
                modifier = Modifier
                    .padding(all = 32.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.Top
                ),
                content = {

                    HNotesOutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = title,
                        onValueChange = onTitleChange,
                        placeholder = {
                            Text(text = stringResource(id = R.string.feature_task_title_placeholder))
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            capitalization = KeyboardCapitalization.Words,
                            autoCorrectEnabled = true,
                            keyboardType = KeyboardType.Text
                        )
                    )

                    HNotesFilledTonalButton(
                        onClick = { onReminderPickerVisibilityChange(true) },
                        text = {
                            Text(
                                text = reminder?.format()
                                    ?: stringResource(id = R.string.feature_task_set_reminder)
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = when (reminder) {
                                    null -> HNotesIcons.Alarm
                                    else -> when (repeatMode) {
                                        RepeatMode.NONE -> HNotesIcons.AlarmOnce
                                        else -> HNotesIcons.AlarmRepeat
                                    }
                                },
                                contentDescription = null,
                                modifier = Modifier.size(size = ButtonDefaults.IconSize)
                            )
                        }
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically,
                        content = {

                            if (completeEnabled)
                                HNotesTextButton(
                                    onClick = onCompleteClick,
                                    text = { Text(text = stringResource(id = R.string.feature_task_mark_as_completed)) }
                                )

                            Spacer(modifier = Modifier.weight(weight = 1f))

                            HNotesTextButton(
                                onClick = onDoneClick,
                                enabled = doneEnabled,
                                text = { Text(text = stringResource(id = R.string.feature_task_done)) }
                            )
                        }
                    )
                }
            )
        }
    )

    if (reminderPickerVisibility) {
        ReminderDateTimePickerDialog(
            reminder = reminder,
            repeatMode = repeatMode,
            onConfirmClick = { newReminder, newRepeatMode ->
                onReminderChange(newReminder)
                onRepeatModeChange(newRepeatMode)
                onReminderPickerVisibilityChange(false)
            },
            onCancelClick = { onReminderPickerVisibilityChange(false) },
            onDeleteClick = {
                onClearReminder()
                onReminderPickerVisibilityChange(false)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ReminderDateTimePickerDialog(
    reminder: Instant?,
    repeatMode: RepeatMode,
    onConfirmClick: (Instant, RepeatMode) -> Unit,
    onCancelClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {

    var newReminder by remember { mutableStateOf(value = reminder ?: Clock.System.now()) }
    var newRepeatMode by remember { mutableStateOf(value = repeatMode) }
    var repeatModeMenuExpanded by remember { mutableStateOf(value = false) }

    HNotesAlertDialog(
        onDismissRequest = onCancelClick,
        confirmButton = {
            HNotesTextButton(
                onClick = { onConfirmClick(newReminder, newRepeatMode) },
                text = {
                    Text(
                        text = stringResource(id = R.string.feature_task_reminder_picker_confirm),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        },
        dismissButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                content = {

                    HNotesTextButton(
                        onClick = onDeleteClick,
                        enabled = reminder != null,
                        text = {
                            Text(
                                text = stringResource(id = R.string.feature_task_reminder_picker_delete)
                            )
                        }
                    )

                    HNotesTextButton(
                        onClick = onCancelClick,
                        text = {
                            Text(
                                text = stringResource(id = R.string.feature_task_reminder_picker_cancel)
                            )
                        }
                    )
                }
            )
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.feature_task_reminder_picker_title),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start
            )
        },
        text = {

            Column(
                modifier = Modifier.padding(all = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.CenterVertically
                ),
                content = {

                    WheelDateTimePicker(
                        startDateTime = reminder?.toJavaLocalDateTime()
                            ?: LocalDateTime.now().plusMinutes(1),
                        yearsRange = IntRange(
                            start = LocalDateTime.now().year,
                            endInclusive = 2100
                        ),
                        onSnappedDateTime = { newReminder = it.toKotlinInstant() }
                    )

                    HNotesExposedDropdownMenuBox(
                        modifier = Modifier.fillMaxWidth(),
                        expanded = repeatModeMenuExpanded,
                        onExpandedChange = { repeatModeMenuExpanded = it },
                        text = {
                            HNotesExposedDropdownMenuTextField(
                                modifier = Modifier.fillMaxWidth(),
                                expanded = repeatModeMenuExpanded,
                                value = stringResource(id = newRepeatMode.id()),
                                onValueChange = {},
                                label = {
                                    Text(text = stringResource(id = R.string.feature_task_reminder_picker_repeat_mode))
                                }
                            )
                        },
                        menu = {
                            HNotesExposedDropdownMenu(
                                expanded = repeatModeMenuExpanded,
                                onDismissRequest = { },
                                content = {
                                    RepeatMode.entries.forEach {
                                        HNotesDropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = stringResource(id = it.id()),
                                                    style = MaterialTheme.typography.bodyLarge
                                                )
                                            },
                                            onClick = {
                                                newRepeatMode = it
                                                repeatModeMenuExpanded = false
                                            },
                                            leadingIcon = {}
                                        )
                                    }
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@DevicePreviews
@Composable
fun TaskScreenPreview() {
    HNotesTheme {
        TaskDialog(
            title = "Title",
            onTitleChange = {},
            reminder = null,
            onReminderChange = {},
            onClearReminder = {},
            repeatMode = RepeatMode.NONE,
            onRepeatModeChange = {},
            reminderPickerVisibility = false,
            onReminderPickerVisibilityChange = {},
            doneEnabled = true,
            onDoneClick = {},
            completeEnabled = true,
            onCompleteClick = {}
        )
    }
}

@ThemePreviews
@Composable
fun ReminderDateTimePickerDialogPreview() {
    HNotesTheme {
        HNotesBackground {
            ReminderDateTimePickerDialog(
                reminder = null,
                repeatMode = RepeatMode.NONE,
                onConfirmClick = { _, _ -> },
                onCancelClick = {},
                onDeleteClick = {}
            )
        }
    }
}