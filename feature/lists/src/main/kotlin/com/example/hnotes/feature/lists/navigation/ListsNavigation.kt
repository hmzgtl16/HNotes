package com.example.hnotes.feature.lists.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.hnotes.core.ui.Route
import com.example.hnotes.feature.lists.ListsRoute

fun NavController.navigateToLists(navOptions: NavOptions? = null) =
    navigate(route = Route.ListsRoute, navOptions = navOptions)

fun NavGraphBuilder.listsScreen(navigateToList: (Long) -> Unit) {

    composable<Route.ListsRoute> {

        ListsRoute(navigateToList = navigateToList)
    }
}