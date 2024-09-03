package com.example.hnotes.core.designsystem.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNotesTopAppBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit,
    actions: @Composable RowScope.() -> Unit,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    isCenterAligned: Boolean = true,
) {

    if (isCenterAligned) {
        CenterAlignedTopAppBar(
            title = title,
            modifier = modifier,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors
        )
    }

    if (!isCenterAligned) {
        TopAppBar(
            title = title,
            modifier = modifier,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNotesSearchAppBar(
    searchBar: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {

    TopAppBar(
        title = searchBar,
        modifier = modifier,
        navigationIcon = navigationIcon,
        colors = colors
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun HNotesSingleActionTopAppBarPreview() {
    HNotesTheme {
        HNotesTopAppBar(
            title = {
                Text(
                    text = stringResource(id = android.R.string.untitled),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = HNotesIcons.Search,
                            contentDescription = "Navigation icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                )
            },
            actions = {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = HNotesIcons.Settings,
                            contentDescription = "Action icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun HNotesMultiActionsTopAppBarPreview() {
    HNotesTheme {
        HNotesTopAppBar(
            title = {
                Text(
                    text = stringResource(id = android.R.string.untitled),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = HNotesIcons.Close,
                            contentDescription = "Navigation icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                )
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = HNotesIcons.PinBorder,
                        contentDescription = "Action 3",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = HNotesIcons.Delete,
                        contentDescription = "Action 3",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = HNotesIcons.MoreVert,
                        contentDescription = "Action 3",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            },
            isCenterAligned = false
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun HNotesSearchAppBarPreview() {
    HNotesTheme {
        HNotesSearchAppBar(
            searchBar = {
                HNotesSearchBar(
                    inputField = {
                        SearchBarDefaults.InputField(
                            query = "",
                            onQueryChange = {},
                            onSearch = {},
                            expanded = false,
                            onExpandedChange = {},
                            placeholder = {
                                Text(text = stringResource(id = android.R.string.search_go))
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = HNotesIcons.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            },
                            trailingIcon = {
                                HNotesIconButton(
                                    onClick = {},
                                    icon = {
                                        Icon(
                                            imageVector = HNotesIcons.Close,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                )
                            }
                        )
                    },
                    expanded = false,
                    onExpandedChange = {},
                    content = {}
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = HNotesIcons.Back,
                            contentDescription = "Navigation icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                )
            }
        )
    }
}