package com.example.hnotes.feature.note.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.hnotes.core.ui.Route
import com.example.hnotes.feature.note.NoteRoute

fun NavController.navigateToNote(id: Long, navOptions: NavOptions? = null) =
    navigate(route = Route.NoteRoute(id = id), navOptions = navOptions)

fun NavGraphBuilder.noteScreen(navigateBack: () -> Unit) {

    composable<Route.NoteRoute> {

        NoteRoute(navigateBack = navigateBack)
    }
}
