package com.example.hnotes.core.datastore

import androidx.datastore.core.CorruptionException
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import kotlin.test.assertEquals

class UserPreferencesSerializerTest {
    private val userPreferencesSerializer = UserPreferencesSerializer()

    @Test
    fun defaultUserPreferences_isEmpty() {
        assertEquals(
            expected = userPreferences {  },
            actual = userPreferencesSerializer.defaultValue,
        )
    }

    @Test
    fun writingAndReadingUserPreferences_outputsCorrectValue() = runTest {
        val expectedUserPreferences = userPreferences {
            uiThemeConfig = UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM
            useDynamicUiTheme = true
        }

        val outputStream = ByteArrayOutputStream()

        expectedUserPreferences.writeTo(outputStream)

        val inputStream = ByteArrayInputStream(outputStream.toByteArray())

        val actualUserPreferences = userPreferencesSerializer.readFrom(input = inputStream)

        assertEquals(
            expected = expectedUserPreferences,
            actual = actualUserPreferences,
        )
    }

    @Test(expected = CorruptionException::class)
    fun readingInvalidUserPreferences_throwsCorruptionException() = runTest {
        userPreferencesSerializer.readFrom(ByteArrayInputStream(byteArrayOf(0)))
    }
}
