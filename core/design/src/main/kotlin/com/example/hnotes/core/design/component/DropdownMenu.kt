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
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.design.theme.LocalBackgroundTheme

@Composable
fun AppDropdownMenu(expanded: Boolean, onDismissRequest: () -> Unit, modifier: Modifier = Modifier, scrollState: ScrollState = rememberScrollState(), content: @Composable ColumnScope.() -> Unit) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        scrollState = scrollState,
        containerColor = LocalBackgroundTheme.current.color,
        modifier = modifier,
        content = content,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppExposedDropdownMenuBox(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    text: @Composable ExposedDropdownMenuBoxScope.() -> Unit,
    menu: @Composable ExposedDropdownMenuBoxScope.() -> Unit,
    modifier: Modifier = Modifier,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
        modifier = modifier,
        content = {
            text()
            menu()
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope.AppExposedDropdownMenuTextField(
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
        colors =
        ExposedDropdownMenuDefaults.textFieldColors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuBoxScope.AppExposedDropdownMenu(expanded: Boolean, onDismissRequest: () -> Unit, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit) {
    ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        content = content,
    )
}

@Composable
fun AppDropdownMenuItem(text: @Composable () -> Unit, onClick: () -> Unit, modifier: Modifier = Modifier, leadingIcon: @Composable (() -> Unit)) {
    DropdownMenuItem(
        text = text,
        onClick = onClick,
        leadingIcon = leadingIcon,
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 8.dp),
        modifier = modifier,
    )
}

@ThemePreviews
@Composable
fun AppDropdownMenuPreview() {
    AppTheme {
        AppDropdownMenu(
            expanded = true,
            onDismissRequest = {},
            content = {
                AppDropdownMenuItem(
                    text = { Text("Edit") },
                    onClick = { /* Handle edit! */ },
                    leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) },
                )
                AppDropdownMenuItem(
                    text = { Text("Settings") },
                    onClick = { /* Handle settings! */ },
                    leadingIcon = { Icon(Icons.Outlined.Settings, contentDescription = null) },
                )
                HorizontalDivider()
                AppDropdownMenuItem(
                    text = { Text("Send Feedback") },
                    onClick = { /* Handle send feedback! */ },
                    leadingIcon = { Icon(Icons.Outlined.Email, contentDescription = null) },
                )
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ThemePreviews
@Composable
fun AppExposedDropdownMenuBoxPreview() {
    AppTheme {
        AppExposedDropdownMenuBox(
            expanded = true,
            onExpandedChange = {},
            text = {
                AppExposedDropdownMenuTextField(
                    expanded = true,
                    value = "Mode",
                    onValueChange = {},
                )
            },
            menu = {
                AppExposedDropdownMenu(
                    expanded = true,
                    onDismissRequest = { },
                    content = {
                        RepeatMode.entries.forEach {
                            AppDropdownMenuItem(
                                text = { Text(it.name) },
                                onClick = {},
                                leadingIcon = {},
                            )
                        }
                    },
                )
            },
        )
    }
}
