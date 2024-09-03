package com.example.hnotes.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import com.example.hnotes.core.designsystem.component.HNotesBackground
import com.example.hnotes.core.designsystem.component.HNotesIconButton
import com.example.hnotes.core.designsystem.component.HNotesIconToggleButton
import com.example.hnotes.core.designsystem.component.HNotesOutlinedTextField
import com.example.hnotes.core.designsystem.component.ThemePreviews
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.model.data.Item

@Composable
fun ItemCard(
    item: Item,
    onItemTitleChange: (title: String) -> Unit,
    onDeleteItemClick: () -> Unit,
    onItemCompletedChange: () -> Unit,
    modifier: Modifier = Modifier
) {

    var isFocused by remember {
        mutableStateOf(value = false)
    }

    CompositionLocalProvider(
        value = LocalContentColor provides
                if (item.isCompleted) LocalContentColor.current.copy(
                    alpha = 0.38f
                )
                else LocalContentColor.current,
        content = {
            HNotesOutlinedTextField(
                modifier = modifier
                    .onFocusChanged { state ->
                        isFocused = state.isFocused
                    },
                value = item.title,
                onValueChange = onItemTitleChange,
                leadingIcon = {
                    HNotesIconToggleButton(
                        checked = item.isCompleted,
                        onCheckedChange = { onItemCompletedChange() },
                        colors = IconButtonDefaults.iconToggleButtonColors(
                           checkedContentColor = LocalContentColor.current,
                        ),
                        icon = {
                            Icon(
                                imageVector = HNotesIcons.Unchecked,
                                contentDescription = null
                            )
                        },
                        checkedIcon = {
                            Icon(
                                imageVector = HNotesIcons.Checked,
                                contentDescription = null
                            )
                        }
                    )
                },
                trailingIcon = {
                    if (isFocused) {
                        HNotesIconButton(
                            onClick = onDeleteItemClick,
                            icon = {
                                Icon(
                                    imageVector = HNotesIcons.Close,
                                    contentDescription = null
                                )
                            }
                        )
                    }
                }
            )
        }
    )
}

@ThemePreviews
@Composable
fun ItemCardPreview() {
    HNotesTheme {
        HNotesBackground(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(unbounded = true),
            content = {
                ItemCard(
                    item = Item(title = "Preview"),
                    onItemTitleChange = {},
                    onDeleteItemClick = {},
                    onItemCompletedChange = {}
                )
            }
        )
    }
}

@ThemePreviews
@Composable
fun ItemCardCompletedPreview() {
    HNotesTheme {
        HNotesBackground(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(unbounded = true),
            content = {
            ItemCard(
                item = Item(title = "Preview", isCompleted = true),
                onItemTitleChange = {},
                onDeleteItemClick = {},
                onItemCompletedChange = {}
            )
        })
    }
}
