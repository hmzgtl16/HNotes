package com.example.hnotes.feature.search.impl

import com.example.hnotes.core.model.SearchResult

sealed interface SearchResultUiState {
    data object Loading : SearchResultUiState
    data object EmptyQuery : SearchResultUiState
    data object LoadFailed : SearchResultUiState
    data class Success(val searchResult: SearchResult = SearchResult()) : SearchResultUiState {
        fun isEmpty(): Boolean = searchResult.isEmpty()
    }
}
