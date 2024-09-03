package com.example.hnotes.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.designsystem.component.HNotesBackground
import com.example.hnotes.core.designsystem.component.HNotesModalBottomSheet
import com.example.hnotes.core.designsystem.component.HNotesOutlinedIconToggleButton
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaletteModalBottomSheet(
    sheetState: SheetState,
    onDismissRequest: () -> Unit,
    backgroundColor: Int?,
    onBackgroundColorChange: (color: Int?) -> Unit,
) {

    val options = listOf(
        Color.Unspecified,
        Color(color = 0xFF77172E),
        Color(color = 0xFF692B17),
        Color(color = 0xFF7C4A03),
        Color(color = 0xFF264D3B),
        Color(color = 0xFF0C625D),
        Color(color = 0xFF256377),
        Color(color = 0xFF284255),
        Color(color = 0xFF472E5B),
        Color(color = 0xFF6C394F),
        Color(color = 0xFF4B443A)
    )

    HNotesModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        titleRes = R.string.core_ui_palette_modal_bottom_sheet_title,
        content = {

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 16.dp,
                    alignment = Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically,
                content = {

                    item {
                        HNotesOutlinedIconToggleButton(
                            checked = backgroundColor == null,
                            onCheckedChange = { onBackgroundColorChange(null) },
                            icon = {
                                Icon(
                                    imageVector = HNotesIcons.FormatColorReset,
                                    contentDescription = null
                                )
                            },
                            checkedIcon = {
                                Icon(
                                    imageVector = HNotesIcons.Check,
                                    contentDescription = null
                                )
                            }
                        )
                    }

                    items(
                        items = options.subList(
                            fromIndex = 1,
                            toIndex = options.size
                        ),
                        itemContent = {
                            HNotesOutlinedIconToggleButton(
                                checked = backgroundColor == it.toArgb(),
                                onCheckedChange = { _ ->
                                    onBackgroundColorChange(it.toArgb())
                                },
                                icon = {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = it,
                                                shape = CircleShape
                                            )
                                            .size(size = 48.dp),
                                    )
                                },
                                checkedIcon = {
                                    Box(
                                        modifier = Modifier
                                            .background(
                                                color = it,
                                                shape = CircleShape
                                            )
                                            .size(size = 48.dp),
                                        contentAlignment = Alignment.Center,
                                        content = {
                                            Icon(HNotesIcons.Check, contentDescription = null)
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@DevicePreviews
@Composable
fun PaletteModalBottomSheetPreview(
    @PreviewParameter(BackgroundColorsPreviewParameterProvider::class)
    backgroundColors: List<Color>,
) {
    HNotesTheme {
        HNotesBackground {
            PaletteModalBottomSheet(
                sheetState = SheetState(
                    skipPartiallyExpanded = false,
                    density = LocalDensity.current,
                    initialValue = SheetValue.Expanded
                ),
                onDismissRequest = {},
                backgroundColor = backgroundColors[1].toArgb(),
                onBackgroundColorChange = {}
            )
        }
    }
}