package com.example.hnotes.feature.lists

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.testing.data.listsTestData
import org.junit.Rule
import org.junit.Test

class ListsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun listsScreen_Loading() {

        composeTestRule.setContent {
            HNotesTheme {

            }
        }

        composeTestRule
            .onNodeWithContentDescription(
                label = composeTestRule.activity.resources.getString(R.string.feature_lists_loading)
            )
            .assertExists()
    }

    @Test
    fun listsScreen_Content() {

        composeTestRule.setContent {
            HNotesTheme {

            }
        }

        composeTestRule
            .onNodeWithText(listsTestData.first().title)
            .assertExists()
    }

    @Test
    fun listsScreen_Empty() {

        composeTestRule.setContent {

        }

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.feature_lists_empty_error),
            )
            .assertExists()

        composeTestRule
            .onNodeWithText(
                composeTestRule.activity.getString(R.string.feature_lists_empty_description),
            )
            .assertExists()
    }
}