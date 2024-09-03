package com.example.hnotes.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.hnotes.feature.notes.R as notesR
import com.example.hnotes.feature.tasks.R as tasksR
import com.example.hnotes.feature.lists.R as listsR
import com.example.hnotes.core.ui.Route
import com.example.hnotes.core.designsystem.icon.HNotesIcons

enum class TopLevelDestination(
    val route: Route,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val iconTextId: Int,
    val titleTextId: Int,
) {
    NOTES(
        route = Route.NotesRoute,
        selectedIcon = HNotesIcons.Notes,
        unselectedIcon = HNotesIcons.NotesBorder,
        iconTextId = notesR.string.feature_notes_title,
        titleTextId = notesR.string.feature_notes_title,
    ),
    TASKS(
        route = Route.TasksRoute,
        selectedIcon = HNotesIcons.Tasks,
        unselectedIcon = HNotesIcons.TasksBorder,
        iconTextId = tasksR.string.feature_tasks_title,
        titleTextId = tasksR.string.feature_tasks_title,
    ),
    Lists(
        route = Route.ListsRoute,
        selectedIcon = HNotesIcons.Lists,
        unselectedIcon = HNotesIcons.ListsBorder,
        iconTextId = listsR.string.feature_lists_title,
        titleTextId = listsR.string.feature_lists_title,
    )
}

fun TopLevelDestination?.isNotNull() = this != null
