package com.example.hnotes.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.R
import com.example.hnotes.core.design.component.AppBackground
import com.example.hnotes.core.design.component.AppGradientBackground
import com.example.hnotes.core.design.component.AppIconButton
import com.example.hnotes.core.design.component.AppTopAppBar
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.navigation.NavigationEvent
import com.example.hnotes.core.navigation.Navigator
import com.example.hnotes.feature.search.api.navigation.SearchNavKey
import com.example.hnotes.feature.settings.api.navigation.SettingsNavKey
import com.example.hnotes.navigation.AppNavDisplay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    appState: AppState,
    navigator: Navigator,
    entryProvider: (NavKey) -> NavEntry<NavKey>,
    modifier: Modifier = Modifier,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
        lifecycleOwner
            .repeatOnLifecycle(
                state = Lifecycle.State.STARTED,
                block = {
                    navigator.events.collect {
                        when (it) {
                            is NavigationEvent.NavigateTo -> {
                                appState.navigationState.backStack.add(it.navKey)

                            }

                            is NavigationEvent.NavigateBack -> {
                                appState.navigationState.backStack.removeLastOrNull()
                            }
                        }
                    }
                }
            )
    }

    val shouldShowTopAppBar = appState.isMainDestination

    val coroutineScope = rememberCoroutineScope()

    AppBackground(
        modifier = modifier,
        content = {
            AppGradientBackground {
                Scaffold(
                    modifier = modifier,
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.onBackground,
                    contentWindowInsets = WindowInsets(0, 0, 0, 0),
                    content = { padding ->
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues = padding)
                                .consumeWindowInsets(paddingValues = padding)
                                .windowInsetsPadding(
                                    insets = WindowInsets.safeDrawing.only(sides = WindowInsetsSides.Horizontal)
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
                                        AppTopAppBar(
                                            title = {
                                                Text(
                                                    text = stringResource(id = R.string.app_name),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            },
                                            navigationIcon = {
                                                AppIconButton(
                                                    onClick = {
                                                        coroutineScope.launch {
                                                            navigator.navigateTo(
                                                                navKey = SearchNavKey
                                                            )
                                                        }
                                                    },
                                                    icon = {
                                                        Icon(
                                                            imageVector = AppIcons.Search,
                                                            contentDescription = null,
                                                            tint = MaterialTheme.colorScheme.onSurface
                                                        )
                                                    }
                                                )
                                            },
                                            actions = {
                                                AppIconButton(
                                                    onClick = {
                                                        coroutineScope.launch {
                                                            navigator.navigateTo(
                                                                navKey = SettingsNavKey
                                                            )
                                                        }
                                                    },
                                                    icon = {
                                                        Icon(
                                                            imageVector = AppIcons.Settings,
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
                                        AppNavDisplay(
                                            appState = appState,
                                            entryProvider = entryProvider
                                        )
                                    }
                                )
                            }
                        )
                    }
                )
            }
        }
    )
}
