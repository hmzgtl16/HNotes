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
package com.example.hnotes.core.datastore

import com.example.hnotes.core.model.Theme

fun UiThemeConfigProto.asTheme(): Theme =
    when (this) {
        UiThemeConfigProto.UI_THEME_CONFIG_UNSPECIFIED -> Theme.FOLLOW_SYSTEM
        UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM -> Theme.FOLLOW_SYSTEM
        UiThemeConfigProto.UI_THEME_CONFIG_LIGHT -> Theme.LIGHT
        UiThemeConfigProto.UI_THEME_CONFIG_DARK -> Theme.DARK
        UiThemeConfigProto.UNRECOGNIZED -> Theme.FOLLOW_SYSTEM
    }

fun Theme.asProto(): UiThemeConfigProto =
    when (this) {
        Theme.FOLLOW_SYSTEM -> UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM
        Theme.LIGHT -> UiThemeConfigProto.UI_THEME_CONFIG_LIGHT
        Theme.DARK -> UiThemeConfigProto.UI_THEME_CONFIG_DARK
    }
