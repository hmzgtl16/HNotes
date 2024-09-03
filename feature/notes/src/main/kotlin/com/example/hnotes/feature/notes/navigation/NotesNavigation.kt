package com.example.hnotes.feature.notes.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.hnotes.core.ui.Route
import com.example.hnotes.feature.notes.NotesRoute

fun NavController.navigateToNotes(navOptions: NavOptions? = null) =
    navigate(route = Route.NotesRoute, navOptions = navOptions)

fun NavGraphBuilder.notesScreen(navigateToNote: (Long) -> Unit) {

    composable<Route.NotesRoute> {

        NotesRoute(navigateToNote = navigateToNote)
    }
}
