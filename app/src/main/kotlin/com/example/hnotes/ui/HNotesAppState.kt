package com.example.hnotes.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.hnotes.core.data.utils.TimeZoneMonitor
import com.example.hnotes.feature.lists.navigation.navigateToLists
import com.example.hnotes.feature.notes.navigation.navigateToNotes
import com.example.hnotes.feature.search.navigation.navigateToSearch
import com.example.hnotes.feature.settings.navigation.navigateToSettings
import com.example.hnotes.feature.tasks.navigation.navigateToTasks
import com.example.hnotes.navigation.TopLevelDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.datetime.TimeZone

@Composable
fun rememberHNotesAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    navController: NavHostController = rememberNavController(),
): HNotesAppState {
    return remember(
        key1 = navController,
        key2 = coroutineScope,
        calculation = {
            HNotesAppState(
                navController = navController,
                coroutineScope = coroutineScope,
            )
        }
    )
}

@Stable
class HNotesAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val topLevelDestinations: List<TopLevelDestination> =
        TopLevelDestination.entries

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = topLevelDestinations.firstOrNull {
            currentDestination?.hasRoute(route = it.route::class) ?: false
        }


    fun navigateToTopLevelDestination(topLevelDestination: TopLevelDestination) {
        val topLevelNavOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (topLevelDestination) {
            TopLevelDestination.NOTES -> navController.navigateToNotes(navOptions = topLevelNavOptions)
            TopLevelDestination.TASKS -> navController.navigateToTasks(navOptions = topLevelNavOptions)
            TopLevelDestination.Lists -> navController.navigateToLists(navOptions = topLevelNavOptions)
        }
    }

    fun navigateToSearch() = navController.navigateToSearch()
    fun navigateToSettings() = navController.navigateToSettings()
}

