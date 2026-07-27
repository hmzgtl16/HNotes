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

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme

@Composable
fun AppButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
        ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        contentPadding =
        if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
        content = {
            ButtonContent(
                text = text,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
            )
        },
    )
}

@Composable
fun AppOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
        ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border =
        BorderStroke(
            width = AppButtonDefaults.OutlinedButtonBorderWidth,
            color =
            if (enabled) {
                MaterialTheme.colorScheme.outline
            } else {
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = AppButtonDefaults.DISABLED_OUTLINED_BUTTON_BORDER_ALPHA,
                )
            },
        ),
        contentPadding =
        if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
        content = {
            ButtonContent(
                text = text,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
            )
        },
    )
}

@Composable
fun AppTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors =
        ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        content = {
            ButtonContent(
                text = text,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
            )
        },
    )
}

@Composable
fun AppFilledTonalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.filledTonalButtonColors(),
        content = {
            ButtonContent(
                text = text,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
            )
        },
    )
}

@Composable
private fun ButtonContent(text: @Composable () -> Unit, leadingIcon: @Composable (() -> Unit)?, trailingIcon: @Composable (() -> Unit)?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement =
        Arrangement.spacedBy(
            space = ButtonDefaults.IconSpacing,
            alignment = Alignment.CenterHorizontally,
        ),
        content = {
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                    content = {
                        leadingIcon()
                    },
                )
            }

            text()

            if (trailingIcon != null) {
                Box(
                    modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                    content = {
                        trailingIcon()
                    },
                )
            }
        },
    )
}

object AppButtonDefaults {
    const val DISABLED_OUTLINED_BUTTON_BORDER_ALPHA = 0.12f
    val OutlinedButtonBorderWidth = 1.dp
}

@ThemePreviews
@Composable
fun AppButtonPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppButton(
                onClick = {},
                text = { Text("Test button") },
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppButtonWithIconPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = {
                    Icon(
                        imageVector = AppIcons.AddNote,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppOutlinedButtonPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppOutlinedButton(
                onClick = {},
                text = { Text("Test button") },
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppOutlinedButtonWithIconPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppOutlinedButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = {
                    Icon(
                        imageVector = AppIcons.AddNote,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppTextButtonPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppTextButton(
                onClick = {},
                text = { Text("Test button") },
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppTextButtonWithIconPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppTextButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = {
                    Icon(
                        imageVector = AppIcons.AddNote,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppFilledTonalButtonPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppFilledTonalButton(
                onClick = {},
                text = { Text("Test button") },
            )
        }
    }
}

@ThemePreviews
@Composable
fun AppFilledTonalButtonWithIconPreview() {
    AppTheme {
        AppBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            AppFilledTonalButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = {
                    Icon(
                        imageVector = AppIcons.AddNote,
                        contentDescription = null,
                    )
                },
            )
        }
    }
}
