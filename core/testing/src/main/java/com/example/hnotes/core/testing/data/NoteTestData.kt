package com.example.hnotes.core.testing.data

import com.example.hnotes.core.model.data.Note
import kotlinx.datetime.Clock

val noteTestData = mapOf(
    true to listOf(
        Note(
            id = 1,
            title = "Kotlin",
            description = "Programming Language.",
            isPinned = true,
            backgroundColor = -8972498
        ),
        Note(
            id = 2,
            title = "JetBrains",
            description = "JetBrains creates intelligent software development tools consistently used and trusted by 11.4 million professionals and 88 Fortune Global Top 100 companies. Our lineup of more than 30 products includes IDEs for most programming languages and technologies, such as IntelliJ IDEA, PyCharm, and others, as well as products for team collaboration, like YouTrack and TeamCity. JetBrains is also known for creating the Kotlin programming language, a cross-platform language used by more than 5 million developers worldwide yearly and recommended by Google as the preferred language for Android development. The company is headquartered in Prague, Czech Republic, and has offices around the world.",
            isPinned = true
        ),
        Note(
            id = 3,
            title = "Spring",
            description = "Spring makes Java simple.",
            isPinned = true
        ),
        Note(
            id = 5,
            title = "Notes",
            description = "Notes is a good helper to manage your schedules and notes. It gives you a quick and simple notepad editing experience when you write notes, memo, email, message, shopping list and to do list. It makes to take a note easier than any other notepad and memo apps.",
            isPinned = true
        ),
        Note(
            id = 7,
            title = "",
            description = "",
            isPinned = true
        ),
        Note(
            id = 8,
            title = "",
            description = "",
            isPinned = true,
            backgroundColor = -8972498
        )
    ),
    false to listOf(
        Note(
            id = 4,
            title = "Data safety",
            description = "Safety starts with understanding how developers collect and share your data. Data privacy and security practices may vary based on your use, region, and age. The developer provided this information and may update it over time.",
            isPinned = false,
            backgroundColor = -8972498
        ),
        Note(
            id = 6,
            title = "Note-taking",
            description = "Note-taking is the practice of recording information from different sources and platforms. By taking notes, the writer records the essence of the information, freeing their mind from having to recall everything.",
            isPinned = false
        )
    )
)