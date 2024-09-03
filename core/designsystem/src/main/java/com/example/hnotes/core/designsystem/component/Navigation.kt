package com.example.hnotes.core.designsystem.component

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.WindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScope
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItemColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme

@Composable
fun RowScope.HNotesNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {

    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationBarItemDefaults.colors(
            selectedIconColor = HNotesNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = HNotesNavigationDefaults.navigationContentColor(),
            selectedTextColor = HNotesNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = HNotesNavigationDefaults.navigationContentColor(),
            indicatorColor = HNotesNavigationDefaults.navigationIndicatorColor(),
        )
    )
}

@Composable
fun HNotesNavigationBar(
    modifier: Modifier = Modifier,
    content: @Composable RowScope.() -> Unit,
) {
    NavigationBar(
        modifier = modifier,
        contentColor = HNotesNavigationDefaults.navigationContentColor(),
        tonalElevation = 0.dp,
        content = content
    )
}

@Composable
fun HNotesNavigationRailItem(
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    alwaysShowLabel: Boolean = true,
    icon: @Composable () -> Unit,
    selectedIcon: @Composable () -> Unit = icon,
    label: @Composable (() -> Unit)? = null,
) {
    NavigationRailItem(
        selected = selected,
        onClick = onClick,
        icon = if (selected) selectedIcon else icon,
        modifier = modifier,
        enabled = enabled,
        label = label,
        alwaysShowLabel = alwaysShowLabel,
        colors = NavigationRailItemDefaults.colors(
            selectedIconColor = HNotesNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = HNotesNavigationDefaults.navigationContentColor(),
            selectedTextColor = HNotesNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = HNotesNavigationDefaults.navigationContentColor(),
            indicatorColor = HNotesNavigationDefaults.navigationIndicatorColor(),
        ),
    )
}

@Composable
fun HNotesNavigationRail(
    modifier: Modifier = Modifier,
    header: @Composable (ColumnScope.() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    NavigationRail(
        modifier = modifier,
        containerColor = Color.Transparent,
        contentColor = HNotesNavigationDefaults.navigationContentColor(),
        header = header,
        content = content,
    )
}

@Composable
fun HNotesNavigationSuiteScaffold(
    navigationSuiteItems: HNotesNavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    windowAdaptiveInfo: WindowAdaptiveInfo = currentWindowAdaptiveInfo(),
    content: @Composable () -> Unit,
) {
    val layoutType = NavigationSuiteScaffoldDefaults
        .calculateFromAdaptiveInfo(windowAdaptiveInfo)

    val navigationSuiteItemColors = NavigationSuiteItemColors(
        navigationBarItemColors = NavigationBarItemDefaults.colors(
            selectedIconColor = HNotesNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = HNotesNavigationDefaults.navigationContentColor(),
            selectedTextColor = HNotesNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = HNotesNavigationDefaults.navigationContentColor(),
            indicatorColor = HNotesNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationRailItemColors = NavigationRailItemDefaults.colors(
            selectedIconColor = HNotesNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = HNotesNavigationDefaults.navigationContentColor(),
            selectedTextColor = HNotesNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = HNotesNavigationDefaults.navigationContentColor(),
            indicatorColor = HNotesNavigationDefaults.navigationIndicatorColor(),
        ),
        navigationDrawerItemColors = NavigationDrawerItemDefaults.colors(
            selectedIconColor = HNotesNavigationDefaults.navigationSelectedItemColor(),
            unselectedIconColor = HNotesNavigationDefaults.navigationContentColor(),
            selectedTextColor = HNotesNavigationDefaults.navigationSelectedItemColor(),
            unselectedTextColor = HNotesNavigationDefaults.navigationContentColor(),
        ),
    )

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            HNotesNavigationSuiteScope(
                navigationSuiteScope = this,
                navigationSuiteItemColors = navigationSuiteItemColors,
            ).run(navigationSuiteItems)
        },
        layoutType = layoutType,
        containerColor = Color.Transparent,
        navigationSuiteColors = NavigationSuiteDefaults.colors(
            navigationBarContentColor = HNotesNavigationDefaults.navigationContentColor(),
            navigationRailContainerColor = Color.Transparent,
        ),
        modifier = modifier,
        content = content,
    )
}

class HNotesNavigationSuiteScope internal constructor(
    private val navigationSuiteScope: NavigationSuiteScope,
    private val navigationSuiteItemColors: NavigationSuiteItemColors,
) {
    fun item(
        selected: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        icon: @Composable () -> Unit,
        selectedIcon: @Composable () -> Unit = icon,
        label: @Composable (() -> Unit)? = null,
    ) = navigationSuiteScope.item(
        selected = selected,
        onClick = onClick,
        icon = {
            if (selected) {
                selectedIcon()
            } else {
                icon()
            }
        },
        label = label,
        colors = navigationSuiteItemColors,
        modifier = modifier,
    )
}

object HNotesNavigationDefaults {

    @Composable
    fun navigationContentColor() = MaterialTheme.colorScheme.onSurfaceVariant

    @Composable
    fun navigationSelectedItemColor() = MaterialTheme.colorScheme.onPrimaryContainer

    @Composable
    fun navigationIndicatorColor() = MaterialTheme.colorScheme.primaryContainer
}

@ThemePreviews
@Composable
fun HNotesNavigationBarPreview() {
    val items = listOf("Notes", "Tasks", "Lists")
    val icons = listOf(
        HNotesIcons.NotesBorder,
        HNotesIcons.TasksBorder,
        HNotesIcons.ListsBorder
    )
    val selectedIcons = listOf(
        HNotesIcons.Notes,
        HNotesIcons.Tasks,
        HNotesIcons.Lists
    )

    HNotesTheme {
        HNotesNavigationBar {
            items.forEachIndexed { index, item ->
                HNotesNavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}

@ThemePreviews
@Composable
fun HNotesNavigationRailPreview() {
    val items = listOf("Notes", "Tasks", "Lists")
    val icons = listOf(
        HNotesIcons.NotesBorder,
        HNotesIcons.TasksBorder,
        HNotesIcons.ListsBorder
    )
    val selectedIcons = listOf(
        HNotesIcons.Notes,
        HNotesIcons.Tasks,
        HNotesIcons.Lists
    )

    HNotesTheme {
        HNotesNavigationRail {
            items.forEachIndexed { index, item ->
                HNotesNavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = item,
                        )
                    },
                    selectedIcon = {
                        Icon(
                            imageVector = selectedIcons[index],
                            contentDescription = item,
                        )
                    },
                    label = { Text(item) },
                    selected = index == 0,
                    onClick = { },
                )
            }
        }
    }
}