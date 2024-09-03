package com.example.hnotes.core.designsystem.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.hnotes.core.designsystem.theme.HNotesTheme

@Composable
fun HNotesSnackbar(
    snackbarData: SnackbarData,
    modifier: Modifier = Modifier,
    containerColor: Color,
    contentColor: Color,
    actionColor: Color,
    actionContentColor: Color
) {
    Snackbar(
        snackbarData = snackbarData,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        actionColor = actionColor,
        actionContentColor = actionContentColor
    )
}