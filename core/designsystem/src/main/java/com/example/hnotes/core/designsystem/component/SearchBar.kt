package com.example.hnotes.core.designsystem.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DockedSearchBar
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
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNotesSearchBar(
    inputField: @Composable () -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    colors: SearchBarColors = SearchBarDefaults.colors(),
    content: @Composable ColumnScope.() -> Unit
) {

    SearchBar(
        modifier = modifier,
        inputField = inputField,
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        colors = colors,
        shape = RoundedCornerShape(size = 32.dp),
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun HNotesSearchBarCollapsedPreview() {
    HNotesTheme {
        HNotesSearchBar(
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
            content = {

            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
private fun HNotesSearchBarExpandedPreview() {
    HNotesTheme {
        HNotesSearchBar(
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        )
    }
}