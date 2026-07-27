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

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

@PhonePreviews
@FoldablePreviews
@TabletPreviews
@DesktopPreviews
annotation class DevicePreviews

@Preview(name = "Phone Light Mode", uiMode = Configuration.UI_MODE_NIGHT_NO, device = Devices.PHONE)
@Preview(name = "Phone Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, device = Devices.PHONE)
annotation class PhonePreviews

@Preview(
    name = "Foldable Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.FOLDABLE,
)
@Preview(
    name = "Foldable Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.FOLDABLE,
)
annotation class FoldablePreviews

@Preview(
    name = "Tablet Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.TABLET,
)
@Preview(
    name = "Tablet Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.TABLET,
)
annotation class TabletPreviews

@Preview(
    name = "Desktop Light Mode",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.DESKTOP,
)
@Preview(
    name = "Desktop Dark Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.DESKTOP,
)
annotation class DesktopPreviews
