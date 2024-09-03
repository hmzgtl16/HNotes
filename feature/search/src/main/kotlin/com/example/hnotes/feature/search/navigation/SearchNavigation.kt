package com.example.hnotes.feature.search.navigation

import androidx.activity.compose.BackHandler
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.hnotes.core.ui.Route
import com.example.hnotes.feature.search.SearchRoute

fun NavController.navigateToSearch(navOptions: NavOptions? = null) =
    navigate(Route.SearchRoute, navOptions)

fun NavGraphBuilder.searchScreen(
    navigateBack: () -> Unit,
    navigateToNote: (id: Long) -> Unit,
    navigateToTask: (id: Long) -> Unit,
    navigateToList: (id: Long) -> Unit
) {

    composable<Route.SearchRoute> {

        BackHandler(onBack = navigateBack)

        SearchRoute(
            navigateBack = navigateBack,
            navigateToNote = navigateToNote,
            navigateToTask = navigateToTask,
            navigateToList = navigateToList
        )
    }
}