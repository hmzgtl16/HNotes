package com.example.hnotes.core.designsystem.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.designsystem.theme.HNotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HNotesModalBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    @StringRes titleRes: Int,
    content: @Composable (ColumnScope) -> Unit
) {

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier,
        sheetState = sheetState,
        dragHandle =  {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Text(
                    text = stringResource(id = titleRes),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal)
                )
                HorizontalDivider(thickness = 1.dp, color = Color.Black)
            }
        },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "ModalBottomSheet")
@Composable
fun HNotesModalBottomSheetPreview() {
    HNotesTheme {
        HNotesModalBottomSheet(
            sheetState = SheetState(
                skipPartiallyExpanded = false,
                density = LocalDensity.current,
                initialValue = SheetValue.Expanded
            ),
            onDismissRequest = {  },
            titleRes = android.R.string.untitled,
            content = {
                
            }
        )
    }
}