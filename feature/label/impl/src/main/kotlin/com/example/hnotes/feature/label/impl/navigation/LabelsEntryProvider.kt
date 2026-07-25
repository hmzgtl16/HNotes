package com.example.hnotes.feature.label.impl.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.rememberViewModelStoreOwner
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.scene.DialogSceneStrategy
import com.example.hnotes.feature.label.api.navigation.LabelNavKey
import com.example.hnotes.feature.label.impl.LabelDialog
import com.example.hnotes.feature.label.impl.LabelViewModel

fun EntryProviderScope<NavKey>.labelEntry() {

    entry<LabelNavKey>(metadata = DialogSceneStrategy.dialog()) {
        val viewModelStoreOwner = rememberViewModelStoreOwner()

        LabelDialog(
            viewModel = hiltViewModel(
                viewModelStoreOwner = viewModelStoreOwner,
                creationCallback = { factory: LabelViewModel.Factory ->
                    factory.create(navKey = it)
                }
            )
        )
    }
}