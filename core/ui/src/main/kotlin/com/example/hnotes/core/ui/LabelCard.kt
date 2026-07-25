package com.example.hnotes.core.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.design.component.AppIconButton
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.model.Label
import com.example.hnotes.core.model.Note

@Composable
fun LabelCard(
    label: Label,
    isSelected: Boolean,
    onToggle: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .toggleable(
                value = isSelected,
                onValueChange = {
                    onToggle()
                },
                role = Role.Checkbox
            ),
        horizontalArrangement = Arrangement.spacedBy(
            16.dp,
            Alignment.Start
        ),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            Checkbox(
                checked = isSelected,
                onCheckedChange = { onToggle() }
            )

            Text(
                text = label.name,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .weight(weight = 1f),
            )
        }
    )
}

@PreviewLightDark
@Composable
fun LabelCardPreview(
    @PreviewParameter(NotesPreviewParameterProvider::class)
    notes: Map<Boolean, List<Note>>
) {
    AppTheme {
        LabelCard(
            label = notes[true]!![1].labels.first(),
            isSelected = true,
            onToggle = {},
        )
    }
}
