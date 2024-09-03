package com.example.hnotes.core.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.designsystem.theme.HNotesTheme

@Composable
fun HNotesCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .toggleable(
                value = checked,
                onValueChange = onCheckedChange,
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        content = {

            Checkbox(
                checked = checked,
                onCheckedChange = null,
                colors = CheckboxDefaults.colors()
            )

            text()
        }
    )
}

@Composable
fun HNotesTriStateCheckbox(
    state: ToggleableState,
    onClick: () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .triStateToggleable(
                state = state,
                onClick = onClick,
                role = Role.Checkbox
            )
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp, alignment = Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        content = {

            TriStateCheckbox(
                state = state,
                onClick = null,
                colors = CheckboxDefaults.colors()
            )

            text()
        }
    )
}

@ThemePreviews
@Composable
fun HNotesCheckboxPreview() {
    HNotesTheme {
        HNotesBackground(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            content = {
                HNotesCheckbox(
                    checked = true,
                    onCheckedChange = {},
                    text = {
                        Text(
                            text = stringResource(id = android.R.string.selectAll),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
            }
        )
    }
}

@ThemePreviews
@Composable
fun HNotesTriStateCheckboxPreview() {
    HNotesTheme {
        HNotesBackground(
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 56.dp),
            content = {
                HNotesTriStateCheckbox(
                    state = ToggleableState.Indeterminate,
                    onClick = {},
                    text = {
                        Text(
                            text = stringResource(id = android.R.string.selectAll),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                )
            }
        )
    }
}