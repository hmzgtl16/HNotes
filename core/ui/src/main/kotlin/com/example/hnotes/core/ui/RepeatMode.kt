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

import androidx.annotation.StringRes
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.model.RepeatMode

fun RepeatMode.icon() =
    when (this) {
        RepeatMode.NONE -> AppIcons.AlarmOnce
        else -> AppIcons.AlarmRepeat
    }

@StringRes
fun RepeatMode.id(): Int =
    when (this) {
        RepeatMode.NONE -> R.string.core_ui_repeat_mode_none
        RepeatMode.DAILY -> R.string.core_ui_repeat_mode_daily
        RepeatMode.WEEKLY -> R.string.core_ui_repeat_mode_weekly
        RepeatMode.MONTHLY -> R.string.core_ui_repeat_mode_monthly
        RepeatMode.YEARLY -> R.string.core_ui_repeat_mode_yearly
    }
