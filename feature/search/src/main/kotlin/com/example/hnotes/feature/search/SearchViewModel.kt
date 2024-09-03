package com.example.hnotes.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.SearchRepository
import com.example.hnotes.core.database.model.ListWithItems
import com.example.hnotes.core.model.data.SearchQuery
import com.example.hnotes.core.model.data.SearchResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow(value = "")
    val searchQuery: StateFlow<String> get() = _searchQuery

    val searchResultUiState: StateFlow<SearchResultUiState> =
        searchQuery.flatMapLatest {
            if (it.length < SEARCH_QUERY_MIN_LENGTH) {
                flowOf(value = SearchResultUiState.EmptyQuery)
            } else {
                searchRepository.getSearchContents(searchQuery = SearchQuery(query = it))
                    .map(SearchResultUiState::Success)
                    .catch { SearchResultUiState.LoadFailed }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = SearchResultUiState.Loading,
        )

    val recentSearchQueriesUiState: StateFlow<SearchQueryUiState> =
        searchRepository.getSearchQueries(limit = RECENT_SEARCH_QUERIES_LIMIT)
            .map(SearchQueryUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
                initialValue = SearchQueryUiState.Loading,
            )

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onSearchTriggered(query: String) = viewModelScope.launch {
        searchRepository.insertOrReplaceSearchQuery(searchQuery = SearchQuery(query = query))
    }

    fun clearRecentSearch(searchQuery: SearchQuery) = viewModelScope.launch {
        searchRepository.clearSearchQuery(searchQuery = searchQuery)
    }

    fun clearAllRecentSearches() = viewModelScope.launch {
        searchRepository.clearAllSearchQueries()
    }
}

sealed interface SearchResultUiState {

    data object Loading : SearchResultUiState
    data object EmptyQuery : SearchResultUiState
    data object LoadFailed : SearchResultUiState
    data class Success(val searchResult: SearchResult = SearchResult()) : SearchResultUiState {
        fun isEmpty(): Boolean = searchResult.isEmpty()
    }
}

sealed interface SearchQueryUiState {

    data object Loading : SearchQueryUiState
    data class Success(val queries: List<SearchQuery> = emptyList()) : SearchQueryUiState
}

private const val SEARCH_QUERY_MIN_LENGTH = 2
private const val RECENT_SEARCH_QUERIES_LIMIT = 10
