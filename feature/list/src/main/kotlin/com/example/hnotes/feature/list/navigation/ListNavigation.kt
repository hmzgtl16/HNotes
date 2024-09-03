package com.example.hnotes.feature.list.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.hnotes.core.ui.Route
import com.example.hnotes.feature.list.ListRoute

fun NavController.navigateToList(id: Long, navOptions: NavOptions? = null) =
    navigate(route = Route.ListRoute(id = id), navOptions = navOptions)

fun NavGraphBuilder.listScreen(navigateBack: () -> Unit) {

    composable<Route.ListRoute> {

        ListRoute(navigateBack = navigateBack)
    }
}