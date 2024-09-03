package com.example.hnotes.core.datastore

import androidx.datastore.core.DataStore
import com.example.hnotes.core.model.data.UiTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HNotesPreferencesDataSource @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>,
) {

    val uiTheme: Flow<UiTheme> = userPreferences.data
        .map {
            when (it.uiThemeConfig) {
                UiThemeConfigProto.UI_THEME_CONFIG_DARK -> UiTheme.DARK
                UiThemeConfigProto.UI_THEME_CONFIG_LIGHT -> UiTheme.LIGHT
                else -> UiTheme.FOLLOW_SYSTEM
            }
        }

    val useDynamicUiTheme: Flow<Boolean> = userPreferences.data
        .map { it.useDynamicUiTheme }

    suspend fun setUiThemeConfig(uiTheme: UiTheme) {
        userPreferences.updateData {
            it.copy {
                uiThemeConfig  = when (uiTheme) {
                    UiTheme.FOLLOW_SYSTEM -> UiThemeConfigProto.UI_THEME_CONFIG_FOLLOW_SYSTEM
                    UiTheme.LIGHT -> UiThemeConfigProto.UI_THEME_CONFIG_LIGHT
                    UiTheme.DARK -> UiThemeConfigProto.UI_THEME_CONFIG_DARK
                }
            }
        }
    }

    suspend fun setDynamicUiTheme(useDynamicUiTheme: Boolean) {
        userPreferences.updateData {
            it.copy {
                this.useDynamicUiTheme = useDynamicUiTheme
            }
        }
    }
}

