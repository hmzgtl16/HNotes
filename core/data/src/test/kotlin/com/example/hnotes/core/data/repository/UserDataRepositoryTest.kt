package com.example.hnotes.core.data.repository

import androidx.datastore.core.DataStoreFactory
import com.example.hnotes.core.datastore.HNotesPreferencesDataSource
import com.example.hnotes.core.datastore.UserPreferencesSerializer
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

class UserDataRepositoryTest {

    private val testScope = TestScope(context = UnconfinedTestDispatcher())

    private lateinit var hNotesPreferencesDataSource: HNotesPreferencesDataSource

    private lateinit var repository: UserDataRepositoryImpl

    @get:Rule
    val tmpFolder: TemporaryFolder = TemporaryFolder.builder().assureDeletion().build()

    @Before
    fun setup() {
        hNotesPreferencesDataSource = HNotesPreferencesDataSource(
            userPreferences = DataStoreFactory.create(
                serializer = UserPreferencesSerializer(),
                scope = testScope,
                produceFile = { tmpFolder.newFile("user_preferences_test.pb") }
            )
        )
        repository = UserDataRepositoryImpl(hNotesPreferencesDataSource = hNotesPreferencesDataSource)
    }

    @Test
    fun userDataRepository_default_ui_theme_is_follow_system() = runTest {

        assertEquals(expected = UiTheme.FOLLOW_SYSTEM, actual = repository.uiTheme.first())
    }

    @Test
    fun userDataRepository_default_use_dynamic_ui_theme_is_false() = runTest {

        assertFalse(actual = repository.useDynamicUiTheme.first())
    }

    @Test
    fun userDataRepository_set_ui_theme_delegates_to_hnotes_preferences() = runTest {
        repository.setUiTheme(uiTheme = UiTheme.DARK)

        assertEquals(expected = UiTheme.DARK, actual = repository.uiTheme.first())
        assertEquals(expected = UiTheme.DARK, actual = hNotesPreferencesDataSource.uiTheme.first())
    }

    @Test
    fun userDataRepository_set_dynamic_ui_theme_delegates_to_hnotes_preferences() = runTest {
        repository.setDynamicUiTheme(useDynamicUiTheme = true)

        assertTrue(actual = repository.useDynamicUiTheme.first())
        assertTrue(actual = hNotesPreferencesDataSource.useDynamicUiTheme.first())
    }
}