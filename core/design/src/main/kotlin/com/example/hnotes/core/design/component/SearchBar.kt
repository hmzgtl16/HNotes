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

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    inputField: @Composable () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: SearchBarColors = SearchBarDefaults.colors(),
    content: @Composable ColumnScope.() -> Unit,
) {
    SearchBar(
        modifier = modifier,
        inputField = inputField,
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        colors = colors,
        shape = RoundedCornerShape(size = 32.dp),
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun AppSearchBarCollapsedPreview() {
    AppTheme {
        AppSearchBar(
            modifier = Modifier.fillMaxWidth(),
            inputField = {
                SearchBarDefaults.InputField(
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    expanded = false,
                    onExpandedChange = {},
                    placeholder = { Text(text = stringResource(id = android.R.string.search_go)) },
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
            content = {
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun AppSearchBarExpandedPreview() {
    AppTheme {
        AppSearchBar(
            modifier = Modifier.fillMaxWidth(),
            inputField = {
                SearchBarDefaults.InputField(
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    expanded = true,
                    onExpandedChange = {},
                    placeholder = { Text(text = stringResource(id = android.R.string.search_go)) },
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
            expanded = true,
            onExpandedChange = {},
            content = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    repeat(4) { idx ->
                        val resultText = "Suggestion $idx"
                        ListItem(
                            headlineContent = { Text(resultText) },
                            supportingContent = { Text("Additional info") },
                            leadingContent = { Icon(Icons.Filled.Star, contentDescription = null) },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent),
                            modifier =
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                        )
                    }
                }
            },
        )
    }
}
