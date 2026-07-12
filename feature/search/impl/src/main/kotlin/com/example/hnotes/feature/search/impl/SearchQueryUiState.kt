package com.example.hnotes.feature.search.impl

import com.example.hnotes.core.model.SearchQuery

sealed interface SearchQueryUiState {
    data object Loading : SearchQueryUiState
    data class Success(val queries: List<SearchQuery> = emptyList()) : SearchQueryUiState
}
