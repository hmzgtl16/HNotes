package com.example.hnotes.core.designsystem.component

import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.IconToggleButtonColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconToggleButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme

@Composable
fun HNotesIconToggleButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)? = null,
    enabled: Boolean = true,
    colors: IconToggleButtonColors = IconButtonDefaults.iconToggleButtonColors(
        checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledContainerColor = if (checked) {
            MaterialTheme.colorScheme.onBackground.copy(
                alpha = HNotesIconButtonDefaults.DISABLED_ICON_BUTTON_CONTAINER_ALPHA,
            )
        } else {
            Color.Transparent
        }
    ),
    icon: @Composable () -> Unit,
    checkedIcon: @Composable () -> Unit = icon
) {

    IconToggleButton(
        checked = checked,
        onCheckedChange = {
            if (onCheckedChange != null) {
                onCheckedChange(it)
            }
        },
        modifier = modifier,
        enabled = enabled,
        colors = colors,
        content = {
            if (checked) checkedIcon() else icon()
        }
    )
}

@Composable
fun HNotesOutlinedIconToggleButton(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: @Composable () -> Unit,
    checkedIcon: @Composable () -> Unit = icon
) {

    OutlinedIconToggleButton(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        enabled = enabled,
        colors = IconButtonDefaults.outlinedIconToggleButtonColors(
            checkedContainerColor = MaterialTheme.colorScheme.primaryContainer,
            checkedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            disabledContainerColor = if (checked) {
                MaterialTheme.colorScheme.onBackground.copy(
                    alpha = HNotesIconButtonDefaults.DISABLED_ICON_BUTTON_CONTAINER_ALPHA,
                )
            } else {
                Color.Transparent
            }
        ),
        content = {
            if (checked) checkedIcon() else icon()
        },
    )
}

object HNotesIconButtonDefaults {
    const val DISABLED_ICON_BUTTON_CONTAINER_ALPHA = 0.12f
}

@ThemePreviews
@Composable
fun HNotesIconToggleButtonPreview() {
    HNotesTheme {
        HNotesIconToggleButton(
            checked = true,
            onCheckedChange = {},
            icon = {
                Icon(
                    imageVector = HNotesIcons.PinBorder,
                    contentDescription = null,
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = HNotesIcons.Pin,
                    contentDescription = null,
                )
            }
        )
    }
}

@ThemePreviews
@Composable
fun HNotesIconToggleButtonUncheckedPreview() {
    HNotesTheme {
        HNotesIconToggleButton(
            checked = false,
            onCheckedChange = {},
            icon = {
                Icon(
                    imageVector = HNotesIcons.PinBorder,
                    contentDescription = null,
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = HNotesIcons.Pin,
                    contentDescription = null,
                )
            }
        )
    }
}

@ThemePreviews
@Composable
fun OutlinedIconToggleButtonPreview() {
    HNotesTheme {
        HNotesOutlinedIconToggleButton(
            checked = true,
            onCheckedChange = {},
            icon = {
                Icon(
                    imageVector = HNotesIcons.FormatColorReset,
                    contentDescription = null,
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = HNotesIcons.Check,
                    contentDescription = null,
                )
            }
        )
    }
}

@ThemePreviews
@Composable
fun OutlinedIconToggleButtonUncheckedPreview() {
    HNotesTheme {
        HNotesOutlinedIconToggleButton(
            checked = false,
            onCheckedChange = {},
            icon = {
                Icon(
                    imageVector = HNotesIcons.FormatColorReset,
                    contentDescription = null,
                )
            },
            checkedIcon = {
                Icon(
                    imageVector = HNotesIcons.Check,
                    contentDescription = null,
                )
            }
        )
    }
}