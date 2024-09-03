/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.hnotes.core.datastore

import androidx.datastore.core.DataStoreFactory
import com.example.hnotes.core.model.data.UiTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class HNotesPreferencesDataSourceTest {

    private val testScope: TestScope = TestScope(UnconfinedTestDispatcher())

    private lateinit var dataStore: HNotesPreferencesDataSource

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        dataStore = HNotesPreferencesDataSource(
            userPreferences = DataStoreFactory.create(
                serializer = UserPreferencesSerializer(),
                scope = testScope,
                produceFile = { tmpFolder.newFile("user_preferences_test.pb") }
            )
        )
    }

    @Test
    fun shouldUseDynamicUiThemeIsFalseByDefault() = runTest {
        assertFalse(actual = dataStore.useDynamicUiTheme.first())
    }

    @Test
    fun userShouldUseDynamicUiThemeIsTrueWhenSet() = runTest {
        dataStore.setDynamicUiTheme(useDynamicUiTheme = true)
        assertTrue(actual = dataStore.useDynamicUiTheme.first())
    }

    @Test
    fun shouldUiThemeIsFollowSystemByDefault() = runTest {
        assertEquals(expected = UiTheme.FOLLOW_SYSTEM, actual = dataStore.uiTheme.first())
    }

    @Test
    fun userShouldSetUiThemeIsLightWhenSet() = runTest {
        dataStore.setUiThemeConfig(uiTheme = UiTheme.LIGHT)
        assertEquals(expected = UiTheme.LIGHT, actual = dataStore.uiTheme.first())
    }

    @Test
    fun userShouldSetUiThemeIsDarkWhenSet() = runTest {
        dataStore.setUiThemeConfig(uiTheme = UiTheme.DARK)
        assertEquals(expected = UiTheme.DARK, actual = dataStore.uiTheme.first())
    }
}
