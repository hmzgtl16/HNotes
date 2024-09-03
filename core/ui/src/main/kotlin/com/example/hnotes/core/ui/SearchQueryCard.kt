package com.example.hnotes.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hnotes.core.designsystem.component.HNotesIconButton
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.model.data.SearchQuery

@Composable
fun SearchQueryCard(
    searchQuery: SearchQuery,
    onClick: (SearchQuery) -> Unit,
    onClear: (SearchQuery) -> Unit,
    modifier: Modifier = Modifier
) {

    ListItem(
        headlineContent = {
            Text(text = searchQuery.query)
        },
        leadingContent = {
            Icon(
                imageVector = HNotesIcons.History,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
        },
        trailingContent = {
            HNotesIconButton(
                onClick = { onClear(searchQuery) },
                icon = {
                    Icon(
                        imageVector = HNotesIcons.Close,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        },
        colors = ListItemDefaults.colors(containerColor = Color.Transparent),
        modifier = modifier.clickable { onClick(searchQuery) }
    )
}