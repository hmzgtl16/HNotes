package com.example.hnotes.core.designsystem.component

import android.view.Menu
import androidx.compose.animation.core.RepeatMode
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuBoxScope
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.designsystem.theme.LocalBackgroundTheme

@Composable
fun HNotesDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    scrollState: ScrollState = rememberScrollState(),
    content: @Composable ColumnScope.() -> Unit,
) {

    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        scrollState = scrollState,
        containerColor = LocalBackgroundTheme.current.color,
        modifier = modifier,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNotesExposedDropdownMenuBox(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    text: @Composable ExposedDropdownMenuBoxScope.() -> Unit,
    menu: @Composable ExposedDropdownMenuBoxScope.() -> Unit,
    modifier: Modifier = Modifier
) {

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
        content = {
            text()
            menu()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope.HNotesExposedDropdownMenuTextField(
    expanded: Boolean,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
) {

    TextField(
        modifier = modifier.menuAnchor(type = MenuAnchorType.PrimaryNotEditable),
        value = value,
        onValueChange = onValueChange,
        readOnly = true,
        singleLine = true,
        label = label,
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
        colors = ExposedDropdownMenuDefaults.textFieldColors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope.HNotesExposedDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {

    ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        content = content
    )
}

@Composable
fun HNotesDropdownMenuItem(
    text: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: @Composable (() -> Unit),
) {

    DropdownMenuItem(
        text = text,
        onClick = onClick,
        leadingIcon = leadingIcon,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
        modifier = modifier
    )
}

@ThemePreviews
@Composable
fun HNotesDropdownMenuPreview() {
    HNotesTheme {
        HNotesDropdownMenu(
            expanded = true,
            onDismissRequest = {},
            content = {

                HNotesDropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = { /* Handle edit! */ },
                    leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }
                )
                HNotesDropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = { /* Handle settings! */ },
                    leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) }
                )
                HorizontalDivider()
                HNotesDropdownMenuItem(
                    text = { Text("Send Feedback") },
                    onClick = { /* Handle send feedback! */ },
                    leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
fun HNotesExposedDropdownMenuBoxPreview() {
    HNotesTheme {
        HNotesExposedDropdownMenuBox(
            expanded = true,
            onExpandedChange = {},
            text = {
                HNotesExposedDropdownMenuTextField(
                    expanded = true,
                    value = "Mode",
                    onValueChange = {}
                )
            },
            menu = {
                HNotesExposedDropdownMenu(
                    expanded = true,
                    onDismissRequest = {  },
                    content = {
                        RepeatMode.entries.forEach {
                            HNotesDropdownMenuItem(
                                text = { Text(it.name) },
                                onClick = {},
                                leadingIcon = {}
                            )
                        }
                    }
                )
            }
        )
    }
}