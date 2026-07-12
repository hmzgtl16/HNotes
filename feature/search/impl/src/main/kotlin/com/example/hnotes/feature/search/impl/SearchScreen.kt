package com.example.hnotes.feature.search.impl

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnotes.core.design.component.AppBackground
import com.example.hnotes.core.design.component.AppIconButton
import com.example.hnotes.core.design.component.AppSearchBar
import com.example.hnotes.core.design.component.AppTextButton
import com.example.hnotes.core.design.icon.AppIcons
import com.example.hnotes.core.design.theme.AppTheme
import com.example.hnotes.core.model.Note
import com.example.hnotes.core.model.SearchQuery
import com.example.hnotes.core.model.SearchResult
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.NoteCard
import com.example.hnotes.core.ui.SearchQueryCard
import com.example.hnotes.core.ui.SearchQueryPreviewParameterProvider
import com.example.hnotes.core.ui.SearchResultPreviewParameterProvider

@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val recentSearchQueriesUiState by viewModel.recentSearchQueriesUiState.collectAsStateWithLifecycle()
    val searchResultUiState by viewModel.searchResultUiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.currentSearchQueryUiState.collectAsStateWithLifecycle()

    BackHandler(onBack = { viewModel.onEvent(SearchScreenEvent.NavigateBack) })

    SearchScreen(
        recentSearchQueriesUiState = recentSearchQueriesUiState,
        searchResultUiState = searchResultUiState,
        searchQuery = searchQuery,
        onEvent = viewModel::onEvent,
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    recentSearchQueriesUiState: SearchQueryUiState,
    searchResultUiState: SearchResultUiState,
    searchQuery: String,
    onEvent: (SearchScreenEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
        content = {
            AppSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchQuery,
                        onQueryChange = { onEvent(SearchScreenEvent.SearchQueryChanged(it)) },
                        onSearch = { onEvent(SearchScreenEvent.SearchTriggered(it)) },
                        expanded = true,
                        onExpandedChange = { },
                        placeholder = {
                            Text(text = stringResource(id = android.R.string.search_go))
                        },
                        leadingIcon = {
                            AppIconButton(
                                onClick = { onEvent(SearchScreenEvent.NavigateBack) },
                                icon = {
                                    Icon(
                                        imageVector = AppIcons.Back,
                                        contentDescription = null,
                                    )
                                }
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                AppIconButton(
                                    onClick = { onEvent(SearchScreenEvent.SearchQueryChanged("")) },
                                    icon = {
                                        Icon(
                                            imageVector = AppIcons.Close,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                )
                            }
                        }
                    )
                },
                expanded = true,
                onExpandedChange = { },
                colors = SearchBarDefaults.colors(containerColor = Color.Transparent),
                content = {
                    Column(
                        modifier = modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top,
                        content = {
                            when (searchResultUiState) {
                                is SearchResultUiState.LoadFailed, SearchResultUiState.Loading -> Unit
                                is SearchResultUiState.EmptyQuery -> {
                                    if (recentSearchQueriesUiState is SearchQueryUiState.Success) {
                                        RecentSearches(
                                            queries = recentSearchQueriesUiState.queries,
                                            onRecentSearchClicked = {
                                                onEvent(SearchScreenEvent.SearchQueryChanged(it.query))
                                                onEvent(SearchScreenEvent.SearchTriggered(it.query))
                                            },
                                            onClearRecentSearch = {
                                                onEvent(
                                                    SearchScreenEvent.ClearRecentSearch(
                                                        it
                                                    )
                                                )
                                            },
                                            onClearAllRecentSearches = { onEvent(SearchScreenEvent.ClearAllRecentSearches) }
                                        )
                                    }
                                }

                                is SearchResultUiState.Success -> {
                                    if (searchResultUiState.isEmpty()) {
                                        SearchResultEmpty(
                                            searchQuery = searchQuery,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 48.dp)
                                        )
                                        if (recentSearchQueriesUiState is SearchQueryUiState.Success) {
                                            RecentSearches(
                                                queries = recentSearchQueriesUiState.queries,
                                                onRecentSearchClicked = {
                                                    onEvent(SearchScreenEvent.SearchQueryChanged(it.query))
                                                    onEvent(SearchScreenEvent.SearchTriggered(it.query))
                                                },
                                                onClearRecentSearch = {
                                                    onEvent(
                                                        SearchScreenEvent.ClearRecentSearch(
                                                            it
                                                        )
                                                    )
                                                },
                                                onClearAllRecentSearches = {
                                                    onEvent(
                                                        SearchScreenEvent.ClearAllRecentSearches
                                                    )
                                                }
                                            )
                                        }
                                    } else {
                                        SearchResultContent(
                                            notes = searchResultUiState.searchResult.notes,
                                            onNoteClick = {
                                                onEvent(
                                                    SearchScreenEvent.NavigateToNote(
                                                        noteId = it
                                                    )
                                                )
                                            },
                                            modifier = Modifier.fillMaxSize()
                                        )
                                    }
                                }
                            }
                        }
                    )
                }
            )
        }
    )
}

@Composable
fun RecentSearches(
    queries: List<SearchQuery>,
    onRecentSearchClicked: (SearchQuery) -> Unit,
    onClearRecentSearch: (SearchQuery) -> Unit,
    onClearAllRecentSearches: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.Top
        ),
        content = {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    content = {
                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold),
                                    block = {
                                        append(text = stringResource(id = R.string.feature_search_recent_searches))
                                    }
                                )
                            }
                        )

                        if (queries.isNotEmpty()) {
                            AppTextButton(
                                onClick = onClearAllRecentSearches,
                                text = {
                                    Text(
                                        text = stringResource(id = R.string.feature_search_clear_all_recent_searches),
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            )
                        }
                    }
                )
            }

            items(
                items = queries,
                key = { it.hashCode() },
                contentType = { it },
                itemContent = {
                    SearchQueryCard(
                        searchQuery = it,
                        onClick = onRecentSearchClicked,
                        onClear = onClearRecentSearch,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            )
        }
    )
}

@Composable
fun SearchResultEmpty(
    searchQuery: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter,
        content = {
            val message = stringResource(id = R.string.feature_search_result_not_found, searchQuery)
            val start = message.indexOf(searchQuery)
            Text(
                text = AnnotatedString(
                    text = message,
                    spanStyles = listOf(
                        AnnotatedString.Range(
                            SpanStyle(fontWeight = FontWeight.Bold),
                            start = start,
                            end = start + searchQuery.length
                        )
                    )
                ),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(vertical = 24.dp),
            )
        }
    )
}

@Composable
fun SearchResultContent(
    notes: List<Note>,
    onNoteClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val staggeredGridState = rememberLazyStaggeredGridState()

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Adaptive(minSize = 250.dp),
        contentPadding = PaddingValues(all = 16.dp),
        verticalItemSpacing = 16.dp,
        state = staggeredGridState,
        horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
        modifier = modifier,
        content = {
            if (notes.isNotEmpty()) {
                item(
                    span = StaggeredGridItemSpan.FullLine,
                    content = {

                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold),
                                    block = {
                                        append(stringResource(id = R.string.feature_search_notes))
                                    }
                                )
                            },
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            ),
                        )
                    }
                )
            }

            items(
                items = notes,
                key = { it.hashCode() },
                contentType = { it },
                itemContent = {
                    NoteCard(
                        note = it,
                        onNoteClick = onNoteClick,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .animateItem()
                    )
                }
            )
        }
    )
}

@DevicePreviews
@Composable
private fun SearchScreenLoadingPreview() {
    AppTheme {
        AppBackground {
            SearchScreen(
                recentSearchQueriesUiState = SearchQueryUiState.Loading,
                searchResultUiState = SearchResultUiState.Loading,
                searchQuery = "",
                onEvent = {}
            )
        }
    }
}

@DevicePreviews
@Composable
private fun SearchScreenEmptyPreview() {
    AppTheme {
        AppBackground {
            SearchScreen(
                recentSearchQueriesUiState = SearchQueryUiState.Loading,
                searchResultUiState = SearchResultUiState.Success(searchResult = SearchResult()),
                searchQuery = "Hello World!",
                onEvent = {}
            )
        }
    }
}

@DevicePreviews
@Composable
private fun SearchScreenEmptyPreview(
    @PreviewParameter(SearchResultPreviewParameterProvider::class)
    searchResult: SearchResult
) {
    AppTheme {
        AppBackground {
            SearchScreen(
                recentSearchQueriesUiState = SearchQueryUiState.Success(),
                searchResultUiState = SearchResultUiState.Success(
                    searchResult = searchResult.copy(
                        notes = searchResult.notes.filter {
                            it.title.contains("ry0") || it.content.contains("ry0")
                        }
                    )
                ),
                searchQuery = "ry0",
                onEvent = {}
            )
        }
    }
}

@DevicePreviews
@Composable
private fun SearchScreenPreview(
    @PreviewParameter(SearchQueryPreviewParameterProvider::class)
    searchQueries: List<SearchQuery>,
) {
    AppTheme {
        AppBackground {
            SearchScreen(
                recentSearchQueriesUiState = SearchQueryUiState.Success(queries = searchQueries),
                searchResultUiState = SearchResultUiState.Loading,
                searchQuery = "",
                onEvent = {}
            )
        }
    }
}