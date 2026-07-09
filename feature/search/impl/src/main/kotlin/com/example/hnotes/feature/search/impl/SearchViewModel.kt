package com.example.hnotes.feature.search.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.SearchRepository
import com.example.hnotes.core.model.SearchQuery
import com.example.hnotes.core.navigation.Navigator3
import com.example.hnotes.feature.note.api.navigation.NoteNavKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val navigator3: Navigator3,
    private val searchRepository: SearchRepository
) : ViewModel() {

    val currentSearchQueryUiState: StateFlow<String>
        field = MutableStateFlow(value = "")

    val searchResultUiState: StateFlow<SearchResultUiState> =
        currentSearchQueryUiState
            .flatMapLatest {
                if (it.length <= SEARCH_QUERY_MIN_LENGTH) {
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
        searchRepository.getAllSearchQueries(limit = RECENT_SEARCH_QUERIES_LIMIT)
            .map(SearchQueryUiState::Success)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
                initialValue = SearchQueryUiState.Loading,
            )

    fun onEvent(event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.SearchQueryChanged ->
                updateSearchQuery(query = event.query)

            is SearchScreenEvent.SearchTriggered ->
                savaSearchQuery(query = event.query)

            is SearchScreenEvent.ClearRecentSearch ->
                deleteSearchQuery(searchQuery = event.searchQuery)

            is SearchScreenEvent.ClearAllRecentSearches ->
                deleteAllSearchQueries()

            is SearchScreenEvent.NavigateToNote ->
                navigateToNote(noteId = event.noteId)

            is SearchScreenEvent.NavigateBack ->
                navigateBack()
        }
    }

    private fun updateSearchQuery(query: String) {
        currentSearchQueryUiState.update { query.trim() }
    }

    private fun savaSearchQuery(query: String) = viewModelScope.launch {
        searchRepository.insertOrReplaceSearchQuery(searchQuery = SearchQuery(query = query))
    }

    private fun deleteSearchQuery(searchQuery: SearchQuery) = viewModelScope.launch {
        searchRepository.delete(searchQuery = searchQuery)
    }

    private fun deleteAllSearchQueries() = viewModelScope.launch {
        searchRepository.deleteAll()
    }

    private fun navigateToNote(noteId: Long) = viewModelScope.launch {
        navigator3.navigateTo(navKey = NoteNavKey(noteId = noteId))
    }

    private fun navigateBack() = viewModelScope.launch {
        navigator3.navigateBack()
    }

    companion object {
        private const val SEARCH_QUERY_MIN_LENGTH = 2
        private const val RECENT_SEARCH_QUERIES_LIMIT = 10
    }
}