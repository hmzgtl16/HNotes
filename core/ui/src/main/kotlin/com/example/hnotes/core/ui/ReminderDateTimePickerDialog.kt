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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.design.component.AppAlertDialog
import com.example.hnotes.core.design.component.AppBackground
import com.example.hnotes.core.design.component.AppDateTimePicker
import com.example.hnotes.core.design.component.AppDropdownMenuItem
import com.example.hnotes.core.design.component.AppExposedDropdownMenu
import com.example.hnotes.core.design.component.AppExposedDropdownMenuBox
import com.example.hnotes.core.design.component.AppExposedDropdownMenuTextField
import com.example.hnotes.core.design.component.AppTextButton
import com.example.hnotes.core.design.component.ThemePreviews
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.model.Reminder
import com.example.hnotes.core.model.RepeatMode
import java.time.LocalDateTime
import kotlin.time.Clock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderDateTimePickerDialog(reminder: Reminder?, onConfirmClick: (Reminder?) -> Unit, onCancelClick: () -> Unit, onDeleteClick: () -> Unit) {
    var newReminder by remember {
        mutableStateOf(
            value =
                reminder ?: Reminder(
                    time = Clock.System.now(),
                    repeatMode = RepeatMode.NONE,
                ),
        )
    }
    var repeatModeMenuExpanded by remember { mutableStateOf(value = false) }

    AppAlertDialog(
        onDismissRequest = onCancelClick,
        confirmButton = {
            AppTextButton(
                onClick = { onConfirmClick(newReminder) },
                text = {
                    Text(
                        text = stringResource(id = R.string.feature_task_reminder_picker_confirm),
                        color = MaterialTheme.colorScheme.primary,
                    )
                },
            )
        },
        dismissButton = {
            Row(
                horizontalArrangement =
                    Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.CenterHorizontally,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    AppTextButton(
                        onClick = onDeleteClick,
                        enabled = reminder != null,
                        text = {
                            Text(
                                text = stringResource(id = R.string.feature_task_reminder_picker_delete),
                            )
                        },
                    )

                    AppTextButton(
                        onClick = onCancelClick,
                        text = {
                            Text(
                                text = stringResource(id = R.string.feature_task_reminder_picker_cancel),
                            )
                        },
                    )
                },
            )
        },
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.feature_task_reminder_picker_title),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Start,
            )
        },
        text = {
            Column(
                modifier = Modifier.padding(all = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =
                    Arrangement.spacedBy(
                        space = 24.dp,
                        alignment = Alignment.CenterVertically,
                    ),
                content = {
                    AppDateTimePicker(
                        startDateTime =
                            reminder?.time?.toJavaLocalDateTime()
                                ?: LocalDateTime.now().plusHours(1),
                        yearsRange =
                            IntRange(
                                start = LocalDateTime.now().year,
                                endInclusive = 2100,
                            ),
                        onSnappedDateTime = {
                            newReminder = newReminder.copy(time = it.toKotlinInstant())
                        },
                    )

                    AppExposedDropdownMenuBox(
                        modifier = Modifier.fillMaxWidth(),
                        expanded = repeatModeMenuExpanded,
                        onExpandedChange = { repeatModeMenuExpanded = it },
                        text = {
                            AppExposedDropdownMenuTextField(
                                modifier = Modifier.fillMaxWidth(),
                                expanded = repeatModeMenuExpanded,
                                value = stringResource(id = newReminder.repeatMode.id()),
                                onValueChange = {},
                                label = {
                                    Text(text = stringResource(id = R.string.feature_task_reminder_picker_repeat_mode))
                                },
                            )
                        },
                        menu = {
                            AppExposedDropdownMenu(
                                expanded = repeatModeMenuExpanded,
                                onDismissRequest = { },
                                content = {
                                    RepeatMode.entries.forEach {
                                        AppDropdownMenuItem(
                                            text = {
                                                Text(
                                                    text = stringResource(id = it.id()),
                                                    style = MaterialTheme.typography.bodyLarge,
                                                )
                                            },
                                            onClick = {
                                                newReminder = newReminder.copy(repeatMode = it)
                                                repeatModeMenuExpanded = false
                                            },
                                            leadingIcon = {},
                                        )
                                    }
                                },
                            )
                        },
                    )
                },
            )
        },
    )
}

@ThemePreviews
@Composable
fun ReminderDateTimePickerDialogPreview() {
    AppTheme {
        AppBackground {
            ReminderDateTimePickerDialog(
                reminder = null,
                onConfirmClick = {},
                onCancelClick = {},
                onDeleteClick = {},
            )
        }
    }
}
