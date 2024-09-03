package com.example.hnotes.feature.task.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.dialog
import com.example.hnotes.core.ui.Route
import com.example.hnotes.feature.task.TaskRoute

fun NavController.navigateToTask(taskId: Long, navOptions: NavOptions? = null) {
    navigate(route = Route.TaskRoute(id = taskId), navOptions = navOptions)
}

fun NavGraphBuilder.taskScreen(navigateBack: () -> Unit) {

    dialog<Route.TaskRoute> {

        TaskRoute(navigateBack = navigateBack)
    }
}
