package com.example.hnotes.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.hnotes.core.model.data.Item
import com.example.hnotes.core.model.data.List
import kotlin.collections.List as KList

class ListsPreviewParameterProvider : PreviewParameterProvider<Map<Boolean, KList<List>>> {

    override val values: Sequence<Map<Boolean, KList<List>>>
        get() = sequenceOf(
            mapOf(
                true to listOf(
                    List(
                        id = 1,
                        title = "Add Kotlinx Libraries",
                        isPinned = true,
                        items = listOf(
                            Item(
                                id = 2,
                                title = "Kotlinx.coroutines",
                                isCompleted = false
                            ),
                            Item(
                                id = 4,
                                title = "Kotlinx.io",
                                isCompleted = false
                            ),
                            Item(
                                id = 1,
                                title = "Kotlinx.datetime",
                                isCompleted = true
                            ),
                            Item(
                                id = 3,
                                title = "Kotlinx.serialization",
                                isCompleted = true
                            )
                        )
                    ),
                    List(
                        id = 2,
                        title = "Install JetBrains IDEs",
                        isPinned = true,
                        items = listOf(
                            Item(
                                id = 2,
                                title = "PyCharm",
                                isCompleted = false
                            ),
                            Item(
                                id = 1,
                                title = "IntelliJ IDEA",
                                isCompleted = true
                            ),
                            Item(
                                id = 3,
                                title = "WebStorm",
                                isCompleted = true
                            ),
                            Item(
                                id = 4,
                                title = "Android Studio",
                                isCompleted = true
                            )
                        )
                    )
                ),
                false to listOf(
                    List(
                        id = 3,
                        title = "Use Jetpack Compose Material 3 Components",
                        isPinned = false,
                        items = listOf(
                            Item(
                                id = 3,
                                title = "Badge",
                                isCompleted = false
                            ),
                            Item(
                                id = 5,
                                title = "Chips",
                                isCompleted = false
                            ),
                            Item(
                                id = 7,
                                title = "Menus",
                                isCompleted = false
                            ),
                            Item(
                                id = 1,
                                title = "Buttons",
                                isCompleted = true
                            ),
                            Item(
                                id = 2,
                                title = "Cards",
                                isCompleted = true
                            ),
                            Item(
                                id = 4,
                                title = "Checkbox",
                                isCompleted = true
                            ),
                            Item(
                                id = 6,
                                title = "Dialogs",
                                isCompleted = true
                            ),
                            Item(
                                id = 8,
                                title = "Snackbars",
                                isCompleted = true
                            ),
                            Item(
                                id = 9,
                                title = "Textfields",
                                isCompleted = true
                            )
                        )
                    ),
                    List(
                        id = 4,
                        title = "Use Jetpack Libraries",
                        isPinned = false,
                        items = listOf(
                            Item(
                                id = 3,
                                title = "Fragment",
                                isCompleted = false
                            ),
                            Item(
                                id = 6,
                                title = "Paging",
                                isCompleted = false
                            ),
                            Item(
                                id = 8,
                                title = "Work Manager",
                                isCompleted = false
                            ),
                            Item(
                                id = 1,
                                title = "Activity",
                                isCompleted = true
                            ),
                            Item(
                                id = 2,
                                title = "Compose",
                                isCompleted = true
                            ),
                            Item(
                                id = 4,
                                title = "Lifecycle",
                                isCompleted = true
                            ),
                            Item(
                                id = 5,
                                title = "Navigation",
                                isCompleted = true
                            ),
                            Item(
                                id = 7,
                                title = "Room",
                                isCompleted = true
                            )
                        )
                    )
                )
            )
        )
}
