package com.example.hnotes.core.design.component

import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.ElevatedAssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme

@Composable
fun AppAssistChip(
    onClick: () -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    colors: ChipColors = AssistChipDefaults.elevatedAssistChipColors()
) {
    ElevatedAssistChip(
        onClick = onClick,
        label = {
            ProvideTextStyle(value = MaterialTheme.typography.labelSmall) {
                label()
            }
        },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = leadingIcon,
        colors = colors
    )
}

@ThemePreviews
@Composable
private fun AppAssistChipPreview() {
    AppTheme {
        AppAssistChip(
            onClick = {},
            label = { Text(text = "Assist Chip") },
            leadingIcon = {
                Icon(
                    imageVector = AppIcons.Label,
                    contentDescription = null
                )
            }
        )
    }
}