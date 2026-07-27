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

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme

@Composable
fun AppBottomAppBar(
    actions: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    floatingActionButton: (@Composable () -> Unit)? = null,
    containerColor: Color = BottomAppBarDefaults.containerColor,
    contentColor: Color = contentColorFor(containerColor),
    contentPadding: PaddingValues = BottomAppBarDefaults.ContentPadding,
) {
    BottomAppBar(
        actions = actions,
        modifier = modifier,
        floatingActionButton = floatingActionButton,
        containerColor = containerColor,
        contentColor = contentColor,
        contentPadding = contentPadding,
    )
}

@ThemePreviews
@Composable
private fun AppBottomAppBarPreview() {
    AppTheme {
        AppBottomAppBar(
            actions = {
                AppIconButton(
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = AppIcons.Checked,
                            contentDescription = "Action 1",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )

                AppIconButton(
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = AppIcons.Reminder,
                            contentDescription = "Action 1",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )
                AppIconButton(
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = AppIcons.PinBorder,
                            contentDescription = "Action 3",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )
                AppIconButton(
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = AppIcons.Copy,
                            contentDescription = "Action 3",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )

                AppIconButton(
                    onClick = { /*TODO*/ },
                    icon = {
                        Icon(
                            imageVector = AppIcons.Delete,
                            contentDescription = "Action 3",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    },
                )
            },
            modifier = Modifier,
        )
    }
}
