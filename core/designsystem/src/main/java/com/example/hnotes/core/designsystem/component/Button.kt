package com.example.hnotes.core.designsystem.component

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
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme

@Composable
fun HNotesButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground,
        ),
        contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
        content = {
            ButtonContent(
                text = text,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }
    )
}

@Composable
fun HNotesOutlinedButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {

    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        border = BorderStroke(
            width = HNotesButtonDefaults.OutlinedButtonBorderWidth,
            color = if (enabled) {
                MaterialTheme.colorScheme.outline
            } else {
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = HNotesButtonDefaults.DISABLED_OUTLINED_BUTTON_BORDER_ALPHA,
                )
            },
        ),
        contentPadding = if (leadingIcon != null) {
            ButtonDefaults.ButtonWithIconContentPadding
        } else {
            ButtonDefaults.ContentPadding
        },
        content = {
            ButtonContent(
                text = text,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }
    )
}

@Composable
fun HNotesTextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {

    TextButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
        ),
        content = {
            ButtonContent(
                text = text,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon
            )
        }
    )
}

@Composable
fun HNotesFilledTonalButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
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
                trailingIcon = trailingIcon
            )
        }
    )
}

@Composable
private fun ButtonContent(
    text: @Composable () -> Unit,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = ButtonDefaults.IconSpacing,
            alignment = Alignment.CenterHorizontally
        ),
        content = {
            if (leadingIcon != null) {
                Box(
                    modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                    content = {
                        leadingIcon()
                    }
                )
            }

            text()

            if (trailingIcon != null) {
                Box(
                    modifier = Modifier.sizeIn(maxHeight = ButtonDefaults.IconSize),
                    content = {
                        trailingIcon()
                    }
                )
            }
        }
    )
}

object HNotesButtonDefaults {
    
    const val DISABLED_OUTLINED_BUTTON_BORDER_ALPHA = 0.12f
    val OutlinedButtonBorderWidth = 1.dp
}

@ThemePreviews
@Composable
fun HNotesButtonPreview() {
    HNotesTheme {
        HNotesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HNotesButton(
                onClick = {},
                text = { Text("Test button") }
            )
        }
    }
}

@ThemePreviews
@Composable
fun HNotesButtonWithIconPreview() {
    HNotesTheme {
        HNotesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HNotesButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = {
                    Icon(
                        imageVector = HNotesIcons.AddNote,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@ThemePreviews
@Composable
fun HNotesOutlinedButtonPreview() {
    HNotesTheme {
        HNotesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HNotesOutlinedButton(
                onClick = {},
                text = { Text("Test button") }
            )
        }
    }
}

@ThemePreviews
@Composable
fun HNotesOutlinedButtonWithIconPreview() {
    HNotesTheme {
        HNotesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HNotesOutlinedButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = {
                    Icon(
                        imageVector = HNotesIcons.AddNote,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@ThemePreviews
@Composable
fun HNotesTextButtonPreview() {
    HNotesTheme {
        HNotesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HNotesTextButton(
                onClick = {},
                text = { Text("Test button") }
            )
        }
    }
}

@ThemePreviews
@Composable
fun HNotesTextButtonWithIconPreview() {
    HNotesTheme {
        HNotesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HNotesTextButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = {
                    Icon(
                        imageVector = HNotesIcons.AddNote,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

@ThemePreviews
@Composable
fun HNotesFilledTonalButtonPreview() {
    HNotesTheme {
        HNotesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HNotesFilledTonalButton(
                onClick = {},
                text = { Text("Test button") }
            )
        }
    }
}

@ThemePreviews
@Composable
fun HNotesFilledTonalButtonWithIconPreview() {
    HNotesTheme {
        HNotesBackground(modifier = Modifier.size(150.dp, 50.dp)) {
            HNotesFilledTonalButton(
                onClick = {},
                text = { Text("Test button") },
                leadingIcon = {
                    Icon(
                        imageVector = HNotesIcons.AddNote,
                        contentDescription = null
                    )
                }
            )
        }
    }
}

