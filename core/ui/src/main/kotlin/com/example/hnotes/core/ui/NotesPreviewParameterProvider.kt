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

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.hnotes.core.model.Item
import com.example.hnotes.core.model.Label
import com.example.hnotes.core.model.Note
import com.example.hnotes.core.model.Reminder
import com.example.hnotes.core.model.RepeatMode
import kotlin.time.Instant

class NotesPreviewParameterProvider : PreviewParameterProvider<Map<Boolean, List<Note>>> {
    override val values: Sequence<Map<Boolean, List<Note>>>
        get() =
            sequenceOf(
                mapOf(
                    true to
                        listOf(
                            Note(
                                id = 1L,
                                title = "Note 1",
                                content = "Content of note 1",
                                backgroundColor = -8972498,
                                pinned = true,
                                labels =
                                listOf(
                                    Label(id = 1L, name = "Label 1"),
                                    Label(id = 2L, name = "Label 2"),
                                ),
                            ),
                            Note(
                                id = 2L,
                                title = "Note 2",
                                content = "Content of note 2",
                                labels =
                                listOf(
                                    Label(id = 1L, name = "Label 1"),
                                    Label(id = 2L, name = "Label 2"),
                                ),
                            ),
                            Note(
                                id = 3L,
                                title = "Note 3",
                                content = "Content of note 3",
                            ),
                            Note(
                                id = 4L,
                                title = "Note 4",
                                content = "Content of note 4",
                                backgroundColor = -8972498,
                            ),
                            Note(
                                id = 5L,
                                title = "Note 5",
                                content = "Content of note 5",
                                pinned = true,
                                reminder =
                                Reminder(
                                    time = Instant.parse("2024-10-01T12:00:00Z"),
                                    repeatMode = RepeatMode.NONE,
                                ),
                                labels =
                                listOf(
                                    Label(id = 1L, name = "Label 1"),
                                    Label(id = 2L, name = "Label 2"),
                                    Label(id = 3L, name = "Label 3"),
                                    Label(id = 4L, name = "Label 4"),
                                ),
                            ),
                            Note(
                                id = 6L,
                                title = "Note 6",
                                content = "Content of note 6",
                                pinned = true,
                                reminder =
                                Reminder(
                                    time = Instant.parse("2024-10-01T12:00:00Z"),
                                    repeatMode = RepeatMode.DAILY,
                                ),
                            ),
                            Note(
                                id = 7L,
                                title = "Note 7",
                                content = "Content of note 7",
                                pinned = true,
                                items =
                                listOf(
                                    Item(
                                        id = 1L,
                                        content = "Item 1",
                                        checked = false,
                                    ),
                                    Item(
                                        id = 2L,
                                        content = "Item 2",
                                        checked = true,
                                    ),
                                    Item(
                                        id = 3L,
                                        content = "Item 3",
                                        checked = true,
                                    ),
                                ),
                            ),
                            Note(
                                id = 8L,
                                title = "Note 8",
                                content = "Content of note 8",
                                pinned = true,
                                items =
                                listOf(
                                    Item(
                                        id = 4L,
                                        content = "Item 4",
                                        checked = false,
                                    ),
                                    Item(
                                        id = 5L,
                                        content = "Item 5",
                                        checked = true,
                                    ),
                                    Item(
                                        id = 6L,
                                        content = "Item 6",
                                        checked = false,
                                    ),
                                ),
                            ),
                        ),
                    false to
                        listOf(
                            Note(
                                id = 9L,
                                title = "Note 9",
                                content = "Content of note 9",
                                pinned = false,
                            ),
                            Note(
                                id = 10L,
                                title = "Note 10",
                                content = "Content of note 10",
                                pinned = false,
                            ),
                            Note(
                                id = 11L,
                                title = "Note 11",
                                content = "Content of note 11",
                                pinned = false,
                                reminder =
                                Reminder(
                                    time = Instant.parse("2024-10-01T12:00:00Z"),
                                    repeatMode = RepeatMode.DAILY,
                                ),
                            ),
                            Note(
                                id = 12L,
                                title = "Note 12",
                                content = "Content of note 12",
                                pinned = false,
                                reminder =
                                Reminder(
                                    time = Instant.parse("2024-10-01T12:00:00Z"),
                                    repeatMode = RepeatMode.WEEKLY,
                                ),
                            ),
                            Note(
                                id = 13L,
                                title = "Note 13",
                                content = "Content of note 13",
                                items =
                                listOf(
                                    Item(
                                        id = 7L,
                                        content = "Item 7",
                                        checked = false,
                                    ),
                                    Item(
                                        id = 8L,
                                        content = "Item 8",
                                        checked = true,
                                    ),
                                    Item(
                                        id = 9L,
                                        content = "Item 9",
                                        checked = false,
                                    ),
                                ),
                            ),
                            Note(
                                id = 14L,
                                title = "Note 14",
                                content = "Content of note 14",
                                items =
                                listOf(
                                    Item(
                                        id = 10L,
                                        content = "Item 10",
                                        checked = false,
                                    ),
                                    Item(
                                        id = 11L,
                                        content = "Item 11",
                                        checked = true,
                                    ),
                                    Item(
                                        id = 12L,
                                        content = "Item 12",
                                        checked = false,
                                    ),
                                ),
                            ),
                        ),
                ),
            )
}
