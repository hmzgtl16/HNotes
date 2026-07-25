package com.example.hnotes.core.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.model.Label

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabelPickerModalBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    allLabels: List<Label>,
    selectedLabels: List<Label>,
    onLabelToggled: (Label) -> Unit,
    modifier: Modifier = Modifier,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        modifier = modifier,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                content = {
                    Text(
                        text = "Labels",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(16.dp)
                    )

                    if (allLabels.isEmpty()) {
                        Text(
                            text = "No labels found",
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            content = {
                                items(
                                    items = allLabels,
                                    key = { it.id }
                                ) { label ->
                                    LabelItem(
                                        label = label,
                                        selected = selectedLabels.contains(label),
                                        onLabelClick = { onLabelToggled(label) }
                                    )
                                }
                            }
                        )
                    }
                }
            )
        }
    )
}

@Composable
private fun LabelItem(
    label: Label,
    selected: Boolean,
    onLabelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onLabelClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Checkbox(
            checked = selected,
            onCheckedChange = null
        )
        Text(
            text = label.name,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}
