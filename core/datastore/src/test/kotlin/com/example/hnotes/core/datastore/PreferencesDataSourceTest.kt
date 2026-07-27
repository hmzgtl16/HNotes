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

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class PreferencesDataSourceTest {
    private val testScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var subject: PreferencesDataSource

    @Before
    fun setup() {
        subject =
            PreferencesDataSource(
                userPreferences = InMemoryDataStore(UserPreferences.getDefaultInstance()),
            )
    }

    @Test
    fun shouldThemeIsFollowSystemByDefault() =
        testScope.runTest {
            assertEquals(expected = Theme.FOLLOW_SYSTEM, actual = subject.userData.first().theme)
        }

    @Test
    fun userShouldThemeIsDarkWhenSet() =
        testScope.runTest {
            subject.setTheme(theme = Theme.DARK)
            assertEquals(expected = Theme.DARK, actual = subject.userData.first().theme)
        }

    @Test
    fun shouldUseDynamicColorFalseByDefault() =
        testScope.runTest {
            assertFalse(actual = subject.userData.first().useDynamicColor)
        }

    @Test
    fun userShouldUseDynamicColorIsTrueWhenSet() =
        testScope.runTest {
            subject.setDynamicColor(useDynamicColor = true)
            assertTrue(actual = subject.userData.first().useDynamicColor)
        }
}
