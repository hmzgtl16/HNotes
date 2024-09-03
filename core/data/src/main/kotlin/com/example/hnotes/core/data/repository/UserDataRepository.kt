package com.example.hnotes.core.data.repository

import android.util.Log
import com.example.hnotes.core.datastore.HNotesPreferencesDataSource
import com.example.hnotes.core.model.data.UiTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface UserDataRepository {

    val uiTheme: Flow<UiTheme>
    val useDynamicUiTheme: Flow<Boolean>
    suspend fun setUiTheme(uiTheme: UiTheme)
    suspend fun setDynamicUiTheme(useDynamicUiTheme: Boolean)
}

internal class UserDataRepositoryImpl @Inject constructor(
    private val hNotesPreferencesDataSource: HNotesPreferencesDataSource
) : UserDataRepository {

    override val uiTheme: Flow<UiTheme> = hNotesPreferencesDataSource.uiTheme


    override val useDynamicUiTheme: Flow<Boolean> = hNotesPreferencesDataSource.useDynamicUiTheme

    override suspend fun setUiTheme(uiTheme: UiTheme) {

        hNotesPreferencesDataSource.setUiThemeConfig(uiTheme = uiTheme)
    }

    override suspend fun setDynamicUiTheme(useDynamicUiTheme: Boolean) {
        hNotesPreferencesDataSource.setDynamicUiTheme(useDynamicUiTheme = useDynamicUiTheme)
    }
}