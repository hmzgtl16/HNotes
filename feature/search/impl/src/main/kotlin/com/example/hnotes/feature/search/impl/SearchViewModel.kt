/*
 * Copyright (c) 2026 GATTAL Hamza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.example.hnotes.feature.search.impl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.SearchRepository
import com.example.hnotes.core.model.SearchQuery
import com.example.hnotes.core.navigation.Navigator
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
class SearchViewModel
@Inject
constructor(private val navigator: Navigator, private val searchRepository: SearchRepository) : ViewModel() {
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
            is SearchScreenEvent.SearchQueryChanged -> {
                updateSearchQuery(query = event.query)
            }

            is SearchScreenEvent.SearchTriggered -> {
                savaSearchQuery(query = event.query)
            }

            is SearchScreenEvent.ClearRecentSearch -> {
                deleteSearchQuery(searchQuery = event.searchQuery)
            }

            is SearchScreenEvent.ClearAllRecentSearches -> {
                deleteAllSearchQueries()
            }

            is SearchScreenEvent.NavigateToNote -> {
                navigateToNote(noteId = event.noteId)
            }

            is SearchScreenEvent.NavigateBack -> {
                navigateBack()
            }
        }
    }

    private fun updateSearchQuery(query: String) {
        currentSearchQueryUiState.update { query.trim() }
    }

    private fun savaSearchQuery(query: String) =
        viewModelScope.launch {
            searchRepository.insertOrReplaceSearchQuery(searchQuery = SearchQuery(query = query))
        }

    private fun deleteSearchQuery(searchQuery: SearchQuery) =
        viewModelScope.launch {
            searchRepository.delete(searchQuery = searchQuery)
        }

    private fun deleteAllSearchQueries() =
        viewModelScope.launch {
            searchRepository.deleteAll()
        }

    private fun navigateToNote(noteId: Long) =
        viewModelScope.launch {
            navigator.navigateTo(navKey = NoteNavKey(noteId = noteId))
        }

    private fun navigateBack() =
        viewModelScope.launch {
            navigator.navigateBack()
        }

    companion object {
        private const val SEARCH_QUERY_MIN_LENGTH = 2
        private const val RECENT_SEARCH_QUERIES_LIMIT = 10
    }
}
