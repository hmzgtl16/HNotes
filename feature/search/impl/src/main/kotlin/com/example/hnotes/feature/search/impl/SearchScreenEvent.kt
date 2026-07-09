package com.example.hnotes.feature.search.impl

import com.example.hnotes.core.model.SearchQuery

sealed interface SearchScreenEvent {
    data class SearchQueryChanged(val query: String) : SearchScreenEvent
    data class SearchTriggered(val query: String) : SearchScreenEvent
    data class ClearRecentSearch(val searchQuery: SearchQuery) : SearchScreenEvent
    data object ClearAllRecentSearches : SearchScreenEvent

    data class NavigateToNote(val noteId: Long) : SearchScreenEvent
    data object NavigateBack : SearchScreenEvent
}