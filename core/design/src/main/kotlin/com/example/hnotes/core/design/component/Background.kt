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
package com.example.hnotes.core.design.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.design.theme.GradientColors
import com.example.hnotes.core.design.theme.LocalBackgroundTheme
import com.example.hnotes.core.design.theme.LocalGradientColors
import kotlin.math.tan

@Composable
fun AppBackground(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val color: Color = LocalBackgroundTheme.current.color
    val tonalElevation: Dp = LocalBackgroundTheme.current.tonalElevation

    Surface(
        color = if (color == Color.Unspecified) Color.Transparent else color,
        tonalElevation = if (tonalElevation == Dp.Unspecified) 0.dp else tonalElevation,
        modifier = modifier.fillMaxSize(),
        content = {
            CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
                content()
            }
        },
    )
}

@Composable
fun AppGradientBackground(modifier: Modifier = Modifier, gradientColors: GradientColors = LocalGradientColors.current, content: @Composable () -> Unit) {
    val currentTopColor by rememberUpdatedState(newValue = gradientColors.top)
    val currentBottomColor by rememberUpdatedState(newValue = gradientColors.bottom)
    Surface(
        color =
        if (gradientColors.container == Color.Unspecified) {
            Color.Transparent
        } else {
            gradientColors.container
        },
        modifier = modifier.fillMaxSize(),
        content = {
            Box(
                modifier =
                Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        val offset = size.height * tan(Math.toRadians(11.06).toFloat())

                        val start = Offset(size.width / 2 + offset / 2, 0f)
                        val end = Offset(size.width / 2 - offset / 2, size.height)

                        val topGradient =
                            Brush.linearGradient(
                                0f to
                                    if (currentTopColor == Color.Unspecified) {
                                        Color.Transparent
                                    } else {
                                        currentTopColor
                                    },
                                0.724f to Color.Transparent,
                                start = start,
                                end = end,
                            )

                        val bottomGradient =
                            Brush.linearGradient(
                                0.2552f to Color.Transparent,
                                1f to
                                    if (currentBottomColor == Color.Unspecified) {
                                        Color.Transparent
                                    } else {
                                        currentBottomColor
                                    },
                                start = start,
                                end = end,
                            )

                        onDrawBehind {
                            drawRect(topGradient)
                            drawRect(bottomGradient)
                        }
                    },
                content = { content() },
            )
        },
    )
}

@ThemePreviews
@Composable
fun BackgroundDefault() {
    AppTheme {
        AppBackground(modifier = Modifier.size(size = 100.dp), content = {})
    }
}

@ThemePreviews
@Composable
fun BackgroundDynamic() {
    AppTheme(enableDynamicTheming = false) {
        AppBackground(modifier = Modifier.size(size = 100.dp), content = {})
    }
}

@ThemePreviews
@Composable
fun GradientBackgroundDefault() {
    AppTheme {
        AppGradientBackground(modifier = Modifier.size(size = 100.dp), content = {})
    }
}

@ThemePreviews
@Composable
fun GradientBackgroundDynamic() {
    AppTheme(enableDynamicTheming = false) {
        AppGradientBackground(modifier = Modifier.size(size = 100.dp), content = {})
    }
}
