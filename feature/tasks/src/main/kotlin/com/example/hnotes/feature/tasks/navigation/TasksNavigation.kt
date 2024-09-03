package com.example.hnotes.feature.tasks.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.hnotes.core.ui.Route
import com.example.hnotes.feature.tasks.TasksRoute

fun NavController.navigateToTasks(navOptions: NavOptions? = null) =
    navigate(route = Route.TasksRoute, navOptions = navOptions)

fun NavGraphBuilder.tasksScreen(navigateToTask: (Long) -> Unit) {

    composable<Route.TasksRoute> {

        TasksRoute(navigateToTask = navigateToTask)
    }
}
