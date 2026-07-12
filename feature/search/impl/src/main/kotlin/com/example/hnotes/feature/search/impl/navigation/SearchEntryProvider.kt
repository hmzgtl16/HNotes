package com.example.hnotes.feature.search.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.example.hnotes.feature.search.api.navigation.SearchNavKey
import com.example.hnotes.feature.search.impl.SearchScreen

fun EntryProviderScope<NavKey>.searchEntry() {
    entry<SearchNavKey> {
        SearchScreen()
    }
}