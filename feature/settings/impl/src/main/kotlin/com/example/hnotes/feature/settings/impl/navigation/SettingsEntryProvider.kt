package com.example.hnotes.feature.settings.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import com.example.hnotes.feature.settings.api.navigation.SettingsNavKey
import com.example.hnotes.feature.settings.impl.SettingsDialog

fun EntryProviderScope<NavKey>.settingsEntry() {
    entry<SettingsNavKey>(metadata = DialogSceneStrategy.dialog()) {
        SettingsDialog()
    }
}