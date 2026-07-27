/*
 * Copyright (c) 2026 GATTAL Hamza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.hnotes.core.design.component

import androidx.compose.foundation.layout.RowScope
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
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppBar(
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
            colors = colors,
        )
    }

    if (!isCenterAligned) {
        TopAppBar(
            title = title,
            modifier = modifier,
            navigationIcon = navigationIcon,
            actions = actions,
            colors = colors,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchAppBar(searchBar: @Composable () -> Unit, modifier: Modifier = Modifier, navigationIcon: @Composable () -> Unit, colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()) {
    TopAppBar(
        title = searchBar,
        modifier = modifier,
        navigationIcon = navigationIcon,
        colors = colors,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun AppSingleActionTopAppBarPreview() {
    AppTheme {
        AppTopAppBar(
            title = {
                Text(
                    text = stringResource(id = android.R.string.untitled),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = AppIcons.Search,
                            contentDescription = "Navigation icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )
            },
            actions = {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = AppIcons.Settings,
                            contentDescription = "Action icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun AppMultiActionsTopAppBarPreview() {
    AppTheme {
        AppTopAppBar(
            title = {
                Text(
                    text = stringResource(id = android.R.string.untitled),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = AppIcons.Close,
                            contentDescription = "Navigation icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )
            },
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = AppIcons.PinBorder,
                        contentDescription = "Action 3",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = AppIcons.Delete,
                        contentDescription = "Action 3",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = AppIcons.MoreVert,
                        contentDescription = "Action 3",
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            },
            isCenterAligned = false,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun AppSearchAppBarPreview() {
    AppTheme {
        AppSearchAppBar(
            searchBar = {
                AppSearchBar(
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
                                    imageVector = AppIcons.Search,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            },
                            trailingIcon = {
                                AppIconButton(
                                    onClick = {},
                                    icon = {
                                        Icon(
                                            imageVector = AppIcons.Close,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurface,
                                        )
                                    },
                                )
                            },
                        )
                    },
                    expanded = false,
                    onExpandedChange = {},
                    content = {},
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = {},
                    content = {
                        Icon(
                            imageVector = AppIcons.Back,
                            contentDescription = "Navigation icon",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )
            },
        )
    }
}
