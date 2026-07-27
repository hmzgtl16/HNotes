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

import androidx.datastore.core.CorruptionException
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

class UserPreferencesSerializerTest {
    @Test
    fun defaultUserPreferences_isEmpty() {
        val serializer = UserPreferencesSerializer()
        assertEquals(
            expected = userPreferences { },
            actual = serializer.defaultValue,
        )
    }

    @Test
    fun writingAndReadingUserPreferences_outputsCorrectValue() =
        runTest {
            val serializer = UserPreferencesSerializer()

            val expected =
                userPreferences {
                    uiThemeConfig = UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM
                    useDynamicUiTheme = true
                }

            val outputStream = ByteArrayOutputStream()

            expected.writeTo(outputStream)

            val inputStream = ByteArrayInputStream(outputStream.toByteArray())

            val actual = serializer.readFrom(input = inputStream)

            assertEquals(expected = expected, actual = actual)
        }

    @Test(expected = CorruptionException::class)
    fun readingInvalidUserPreferences_throwsCorruptionException() =
        runTest {
            val serializer = UserPreferencesSerializer()

            serializer.readFrom(ByteArrayInputStream(byteArrayOf(0)))
        }
}
