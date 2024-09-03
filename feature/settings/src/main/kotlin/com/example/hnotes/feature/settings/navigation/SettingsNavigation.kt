package com.example.hnotes.feature.settings.navigation

import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.dialog
import com.example.hnotes.core.ui.Route
import com.example.hnotes.feature.settings.SettingsRoute

fun NavController.navigateToSettings(navOptions: NavOptions? = null) =
    navigate(route = Route.SettingsRoute, navOptions = navOptions)

fun NavGraphBuilder.settingsDialog(onDismiss: () -> Unit) {

    dialog<Route.SettingsRoute>(
        dialogProperties = DialogProperties(usePlatformDefaultWidth = false),
        content = {

            SettingsRoute(onDismiss = onDismiss)
        }
    )
}