package com.example.hnotes.core.ui

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.hnotes.core.database.model.ListWithItems
import com.example.hnotes.core.model.data.Item
import com.example.hnotes.core.model.data.List
import com.example.hnotes.core.model.data.Note
import com.example.hnotes.core.model.data.RepeatMode
import com.example.hnotes.core.model.data.SearchResult
import com.example.hnotes.core.model.data.Task
import kotlinx.datetime.Clock
import kotlin.time.Duration.Companion.hours

class SearchResultPreviewParameterProvider: PreviewParameterProvider<SearchResult> {

    override val values: Sequence<SearchResult> = sequenceOf(
        SearchResult(
            notes = listOf(
                Note(
                    id = 881,
                    title = "Kotlin",
                    description = "Programming Language.",
                    isPinned = true,
                    backgroundColor = -8972498
                ),
                Note(
                    id = 882,
                    title = "JetBrains",
                    description = "JetBrains creates intelligent software development tools consistently used and trusted by 11.4 million professionals and 88 Fortune Global Top 100 companies. Our lineup of more than 30 products includes IDEs for most programming languages and technologies, such as IntelliJ IDEA, PyCharm, and others, as well as products for team collaboration, like YouTrack and TeamCity. JetBrains is also known for creating the Kotlin programming language, a cross-platform language used by more than 5 million developers worldwide yearly and recommended by Google as the preferred language for Android development. The company is headquartered in Prague, Czech Republic, and has offices around the world.",
                    isPinned = true
                ),
                Note(
                    id = 883,
                    title = "Spring",
                    description = "Spring makes Java simple.",
                    isPinned = true
                ),
                Note(
                    id = 884,
                    title = "Data safety",
                    description = "Safety starts with understanding how developers collect and share your data. Data privacy and security practices may vary based on your use, region, and age. The developer provided this information and may update it over time.",
                    isPinned = false,
                    backgroundColor = -8972498
                ),
                Note(
                    id = 885,
                    title = "Notes",
                    description = "Notes is a good helper to manage your schedules and notes. It gives you a quick and simple notepad editing experience when you write notes, memo, email, message, shopping list and to do list. It makes to take a note easier than any other notepad and memo apps.",
                    isPinned = true
                ),
                Note(
                    id = 886,
                    title = "Note-taking",
                    description = "Note-taking is the practice of recording information from different sources and platforms. By taking notes, the writer records the essence of the information, freeing their mind from having to recall everything.",
                    isPinned = false
                )
            ),
            tasks = listOf(
                Task(
                    id = 991,
                    title = "Task 1",
                    created = Clock.System.now(),
                    updated = Clock.System.now(),
                    reminder = Clock.System.now().minus(1.hours),
                    repeatMode = RepeatMode.NONE,
                    isCompleted = false,
                ),
                Task(
                    id = 992,
                    title = "Task 2",
                    created = Clock.System.now(),
                    updated = Clock.System.now(),
                    reminder = Clock.System.now(),
                    repeatMode = RepeatMode.DAILY,
                    isCompleted = false,
                ),
                Task(
                    id = 993,
                    title = "Task 3",
                    created = Clock.System.now(),
                    updated = Clock.System.now(),
                    reminder = Clock.System.now(),
                    repeatMode = RepeatMode.WEEKLY,
                    isCompleted = false,
                ),
                Task(
                    id = 994,
                    title = "Task 4",
                    created = Clock.System.now(),
                    updated = Clock.System.now(),
                    reminder = Clock.System.now(),
                    repeatMode = RepeatMode.MONTHLY,
                    isCompleted = true,
                ),
                Task(
                    id = 995,
                    title = "Task 5",
                    created = Clock.System.now(),
                    updated = Clock.System.now(),
                    reminder = Clock.System.now(),
                    repeatMode = RepeatMode.YEARLY,
                    isCompleted = true,
                ),
                Task(
                    id = 996,
                    title = "Task 6",
                    created = Clock.System.now(),
                    updated = Clock.System.now(),
                    reminder = Clock.System.now(),
                    repeatMode = RepeatMode.YEARLY,
                    isCompleted = true,
                )
            ),
            lists =  listOf(
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
                ),
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