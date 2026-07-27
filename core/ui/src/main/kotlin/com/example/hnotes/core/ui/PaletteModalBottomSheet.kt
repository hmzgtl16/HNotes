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
import com.example.hnotes.core.design.component.AppBackground
import com.example.hnotes.core.design.component.AppModalBottomSheet
import com.example.hnotes.core.design.component.AppOutlinedIconToggleButton
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaletteModalBottomSheet(sheetState: SheetState, onDismissRequest: () -> Unit, backgroundColor: Int?, onBackgroundColorChange: (color: Int?) -> Unit) {
    val options =
        listOf(
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
            Color(color = 0xFF4B443A),
        )

    AppModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismissRequest,
        titleRes = R.string.core_ui_palette_modal_bottom_sheet_title,
        content = {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 16.dp),
                horizontalArrangement =
                    Arrangement.spacedBy(
                        space = 16.dp,
                        alignment = Alignment.CenterHorizontally,
                    ),
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    item {
                        AppOutlinedIconToggleButton(
                            checked = backgroundColor == null,
                            onCheckedChange = { onBackgroundColorChange(null) },
                            icon = {
                                Icon(
                                    imageVector = AppIcons.FormatColorReset,
                                    contentDescription = null,
                                )
                            },
                            checkedIcon = {
                                Icon(
                                    imageVector = AppIcons.Check,
                                    contentDescription = null,
                                )
                            },
                        )
                    }

                    items(
                        items =
                            options.subList(
                                fromIndex = 1,
                                toIndex = options.size,
                            ),
                        itemContent = {
                            AppOutlinedIconToggleButton(
                                checked = backgroundColor == it.toArgb(),
                                onCheckedChange = { _ ->
                                    onBackgroundColorChange(it.toArgb())
                                },
                                icon = {
                                    Box(
                                        modifier =
                                            Modifier
                                                .background(
                                                    color = it,
                                                    shape = CircleShape,
                                                )
                                                .size(size = 48.dp),
                                    )
                                },
                                checkedIcon = {
                                    Box(
                                        modifier =
                                            Modifier
                                                .background(
                                                    color = it,
                                                    shape = CircleShape,
                                                )
                                                .size(size = 48.dp),
                                        contentAlignment = Alignment.Center,
                                        content = {
                                            Icon(AppIcons.Check, contentDescription = null)
                                        },
                                    )
                                },
                            )
                        },
                    )
                },
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@DevicePreviews
@Composable
fun PaletteModalBottomSheetPreview(
    @PreviewParameter(BackgroundColorsPreviewParameterProvider::class)
    backgroundColors: List<Color>,
) {
    AppTheme {
        AppBackground {
            PaletteModalBottomSheet(
                sheetState =
                    SheetState(
                        skipPartiallyExpanded = false,
                        density = LocalDensity.current,
                        initialValue = SheetValue.Expanded,
                    ),
                onDismissRequest = {},
                backgroundColor = backgroundColors[1].toArgb(),
                onBackgroundColorChange = {},
            )
        }
    }
}
