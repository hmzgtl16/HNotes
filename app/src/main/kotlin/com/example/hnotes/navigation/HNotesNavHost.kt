package com.example.hnotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import com.example.hnotes.core.ui.Route
import com.example.hnotes.feature.list.navigation.listScreen
import com.example.hnotes.feature.list.navigation.navigateToList
import com.example.hnotes.feature.lists.navigation.listsScreen
import com.example.hnotes.feature.note.navigation.navigateToNote
import com.example.hnotes.feature.note.navigation.noteScreen
import com.example.hnotes.feature.notes.navigation.notesScreen
import com.example.hnotes.feature.search.navigation.searchScreen
import com.example.hnotes.feature.settings.navigation.settingsDialog
import com.example.hnotes.feature.task.navigation.navigateToTask
import com.example.hnotes.feature.task.navigation.taskScreen
import com.example.hnotes.feature.tasks.navigation.tasksScreen
import com.example.hnotes.ui.HNotesAppState

@Composable
fun HNotesNavHost(
    appState: HNotesAppState,
    modifier: Modifier = Modifier,
    startDestination: Route = Route.NotesRoute
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        builder = {

            listsScreen(navigateToList = navController::navigateToList)
            notesScreen(navigateToNote = navController::navigateToNote)
            tasksScreen(navigateToTask = navController::navigateToTask)

            listScreen(navigateBack = navController::navigateBack)
            noteScreen(navigateBack = navController::navigateBack)
            taskScreen(navigateBack = navController::navigateBack)

            searchScreen(
                navigateBack = navController::navigateBack,
                navigateToNote = navController::navigateToNote,
                navigateToTask = navController::navigateToTask,
                navigateToList = navController::navigateToList
            )
            settingsDialog(onDismiss = navController::navigateBack)
        }
    )
}

fun NavController.navigateBack() = navigateUp()