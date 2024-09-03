package com.example.hnotes.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import com.example.hnotes.core.designsystem.component.HNotesBackground
import com.example.hnotes.core.designsystem.component.HNotesGradientBackground
import com.example.hnotes.core.designsystem.component.HNotesIconButton
import com.example.hnotes.core.designsystem.component.HNotesNavigationSuiteScaffold
import com.example.hnotes.core.designsystem.component.HNotesSearchBar
import com.example.hnotes.core.designsystem.component.HNotesTopAppBar
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.GradientColors
import com.example.hnotes.core.designsystem.theme.LocalGradientColors
import com.example.hnotes.navigation.HNotesNavHost
import com.example.hnotes.navigation.TopLevelDestination
import com.example.hnotes.navigation.isNotNull

@Composable
fun HNotesApp(
    appState: HNotesAppState,
    modifier: Modifier = Modifier,
) {
    val shouldShowGradientBackground = appState.currentTopLevelDestination != null

    HNotesBackground(
        modifier = modifier,
        content = {
            HNotesGradientBackground(
                gradientColors = if (shouldShowGradientBackground) LocalGradientColors.current
                else GradientColors(),
                content = {
                    HNotesApp(
                        appState = appState,
                        windowAdaptiveInfo = currentWindowAdaptiveInfo(),
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HNotesApp(
    appState: HNotesAppState,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo,
) {

    val currentDestination = appState.currentDestination

    val destination = appState.currentTopLevelDestination

    val shouldShowTopAppBar = appState.currentTopLevelDestination.isNotNull()

    HNotesNavigationSuiteScaffold(
        navigationSuiteItems = {
            appState.topLevelDestinations.forEach { destination ->
                val selected = currentDestination
                    .isTopLevelDestinationInHierarchy(destination = destination)

                item(
                    selected = selected,
                    onClick = {
                        appState.navigateToTopLevelDestination(topLevelDestination = destination)
                    },
                    icon = {
                        Icon(
                            imageVector = destination.unselectedIcon,
                            contentDescription = null,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = destination.selectedIcon,
                            contentDescription = null,
                        )
                    },
                    label = { Text(text = stringResource(id = destination.iconTextId)) },
                    modifier = Modifier
                )
            }
        },
        windowAdaptiveInfo = windowAdaptiveInfo,
        content = {
            Scaffold(
                modifier = modifier,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                contentWindowInsets = WindowInsets(left = 0, top = 0, right = 0, bottom = 0),
                content = { padding ->

                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(paddingValues = padding)
                            .consumeWindowInsets(paddingValues = padding)
                            .windowInsetsPadding(
                                insets = WindowInsets.safeDrawing
                                    .only(sides = WindowInsetsSides.Horizontal)
                            ),
                        content = {

                            AnimatedVisibility(
                                visible = shouldShowTopAppBar,
                                enter = slideInVertically(
                                    initialOffsetY = { -it },
                                    animationSpec = tween(
                                        durationMillis = 150,
                                        easing = LinearOutSlowInEasing
                                    )
                                ),
                                exit = slideOutVertically(
                                    targetOffsetY = { -it },
                                    animationSpec = tween(
                                        durationMillis = 250,
                                        easing = FastOutLinearInEasing
                                    )
                                ),
                                content = {
                                    destination?.let {
                                        HNotesTopAppBar(
                                            title = {
                                                Text(
                                                    text = stringResource(id = it.titleTextId),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            },
                                            navigationIcon = {
                                                IconButton(
                                                    onClick = appState::navigateToSearch,
                                                    content = {
                                                        Icon(
                                                            imageVector = HNotesIcons.Search,
                                                            contentDescription = null,
                                                            tint = MaterialTheme.colorScheme.onSurface
                                                        )
                                                    }
                                                )
                                            },
                                            actions = {
                                                IconButton(
                                                    onClick = appState::navigateToSettings,
                                                    content = {
                                                        Icon(
                                                            imageVector = HNotesIcons.Settings,
                                                            contentDescription = null,
                                                            tint = MaterialTheme.colorScheme.onSurface
                                                        )
                                                    }
                                                )
                                            },
                                            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                                                containerColor = Color.Transparent
                                            )
                                        )
                                    }
                                }
                            )

                            Box(
                                modifier = Modifier.consumeWindowInsets(
                                    if (shouldShowTopAppBar) {
                                        WindowInsets.safeDrawing.only(sides = WindowInsetsSides.Top)
                                    } else {
                                        WindowInsets(left = 0, top = 0, right = 0, bottom = 0)
                                    },
                                ),
                                content = {
                                    HNotesNavHost(appState = appState)
                                },
                            )
                        },
                    )
                },
            )
        },
    )
}

private fun NavDestination?.isTopLevelDestinationInHierarchy(
    destination: TopLevelDestination,
): Boolean = this?.hierarchy?.any { it.hasRoute(destination.route::class) } ?: false
