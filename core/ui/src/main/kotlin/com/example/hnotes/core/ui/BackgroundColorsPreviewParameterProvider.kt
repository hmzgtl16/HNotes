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

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class BackgroundColorsPreviewParameterProvider : PreviewParameterProvider<List<Color>> {
    override val values: Sequence<List<Color>>
        get() =
            sequenceOf(
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
                ),
            )
}
