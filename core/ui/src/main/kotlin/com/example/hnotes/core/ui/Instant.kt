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

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.DayOfWeekNames
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

val formatter =
    LocalDateTime.Format {
        date(
            format =
                LocalDate.Format {
                    dayOfWeek(names = DayOfWeekNames.ENGLISH_ABBREVIATED)
                    char(value = Char(code = 44))
                    char(value = Char(code = 32))
                    monthName(names = MonthNames.ENGLISH_ABBREVIATED)
                    char(value = Char(code = 32))
                    dayOfMonth()
                    char(value = Char(code = 44))
                    char(value = Char(code = 32))
                    year()
                },
        )
        char(value = Char(code = 32))
        time(
            format =
                LocalTime.Format {
                    hour()
                    char(value = Char(code = 58))
                    minute()
                },
        )
    }

fun java.time.LocalDateTime.toKotlinInstant(): Instant = toKotlinLocalDateTime().toInstant(timeZone = TimeZone.currentSystemDefault())

fun Instant.toJavaLocalDateTime(): java.time.LocalDateTime = toLocalDateTime(timeZone = TimeZone.currentSystemDefault()).toJavaLocalDateTime()
