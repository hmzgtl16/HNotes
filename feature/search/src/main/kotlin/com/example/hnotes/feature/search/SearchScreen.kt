package com.example.hnotes.feature.search

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
import androidx.compose.material3.IconButton
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
import kotlin.collections.List as KList
import com.example.hnotes.core.designsystem.component.HNotesBackground
import com.example.hnotes.core.designsystem.component.HNotesIconButton
import com.example.hnotes.core.designsystem.component.HNotesLoadingWheel
import com.example.hnotes.core.designsystem.component.HNotesSearchBar
import com.example.hnotes.core.designsystem.component.HNotesTextButton
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.model.data.List
import com.example.hnotes.core.model.data.Note
import com.example.hnotes.core.model.data.SearchQuery
import com.example.hnotes.core.model.data.SearchResult
import com.example.hnotes.core.model.data.Task
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.ListSearchCard
import com.example.hnotes.core.ui.NoteCard
import com.example.hnotes.core.ui.SearchQueryCard
import com.example.hnotes.core.ui.SearchQueryPreviewParameterProvider
import com.example.hnotes.core.ui.SearchResultPreviewParameterProvider
import com.example.hnotes.core.ui.TaskSearchCard

@Composable
internal fun SearchRoute(
    navigateBack: () -> Unit,
    navigateToNote: (id: Long) -> Unit,
    navigateToTask: (id: Long) -> Unit,
    navigateToList: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
) {

    val recentSearchQueriesUiState by viewModel.recentSearchQueriesUiState.collectAsStateWithLifecycle()
    val searchResultUiState by viewModel.searchResultUiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    SearchScreen(
        modifier = modifier,
        recentSearchQueriesUiState = recentSearchQueriesUiState,
        searchResultUiState = searchResultUiState,
        searchQuery = searchQuery,
        onSearchQueryChanged = viewModel::onSearchQueryChanged,
        onSearchTriggered = viewModel::onSearchTriggered,
        onClearRecentSearch = viewModel::clearRecentSearch,
        onClearAllRecentSearches = viewModel::clearAllRecentSearches,
        onBackClick = navigateBack,
        onNoteClick = navigateToNote,
        onTaskClick = navigateToTask,
        onListClick = navigateToList
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SearchScreen(
    modifier: Modifier = Modifier,
    recentSearchQueriesUiState: SearchQueryUiState,
    searchResultUiState: SearchResultUiState,
    searchQuery: String,
    onSearchQueryChanged: (String) -> Unit = {},
    onSearchTriggered: (String) -> Unit = {},
    onClearRecentSearch: (SearchQuery) -> Unit,
    onClearAllRecentSearches: () -> Unit = {},
    onBackClick: () -> Unit,
    onNoteClick: (id: Long) -> Unit,
    onTaskClick: (id: Long) -> Unit,
    onListClick: (id: Long) -> Unit
) {

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter,
        content = {

            HNotesSearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchQuery,
                        onQueryChange = onSearchQueryChanged,
                        onSearch = onSearchTriggered,
                        expanded = true,
                        onExpandedChange = { },
                        placeholder = {
                            Text(text = stringResource(id = android.R.string.search_go))
                        },
                        leadingIcon = {

                            IconButton(
                                onClick = onBackClick,
                                content = {
                                    Icon(
                                        imageVector = HNotesIcons.Back,
                                        contentDescription = null,
                                    )
                                }
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                HNotesIconButton(
                                    onClick = { onSearchQueryChanged("") },
                                    icon = {
                                        Icon(
                                            imageVector = HNotesIcons.Close,
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
                                SearchResultUiState.LoadFailed, SearchResultUiState.Loading -> Unit
                                SearchResultUiState.EmptyQuery -> {
                                    if (recentSearchQueriesUiState is SearchQueryUiState.Success) {
                                        RecentSearches(
                                            queries = recentSearchQueriesUiState.queries,
                                            onRecentSearchClicked = {
                                                onSearchQueryChanged(it.query)
                                                onSearchTriggered(it.query)
                                            },
                                            onClearRecentSearch = onClearRecentSearch,
                                            onClearAllRecentSearches = onClearAllRecentSearches
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
                                                    onSearchQueryChanged(it.query)
                                                    onSearchTriggered(it.query)
                                                },
                                                onClearRecentSearch = onClearRecentSearch,
                                                onClearAllRecentSearches = onClearAllRecentSearches
                                            )
                                        }
                                    } else {
                                        SearchResultContent(
                                            notes = searchResultUiState.searchResult.notes,
                                            tasks = searchResultUiState.searchResult.tasks,
                                            lists = searchResultUiState.searchResult.lists,
                                            onNoteClick = onNoteClick,
                                            onTaskClick = onTaskClick,
                                            onListClick = onListClick,
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
    queries: KList<SearchQuery>,
    onRecentSearchClicked: (SearchQuery) -> Unit,
    onClearRecentSearch: (SearchQuery) -> Unit,
    onClearAllRecentSearches: () -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 8.dp, alignment = Alignment.Top),
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

                            HNotesTextButton(
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
fun SearchResultLoading(modifier: Modifier = Modifier) {

    HNotesLoadingWheel(
        modifier = modifier,
        contentDescription = stringResource(id = R.string.feature_search_loading_search_result)
    )
}

@Composable
fun SearchResultEmpty(
    searchQuery: String,
    modifier: Modifier = Modifier,
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
        },
    )
}

@Composable
fun SearchResultContent(
    notes: KList<Note>,
    tasks: KList<Task>,
    lists: KList<List>,
    onNoteClick: (id: Long) -> Unit,
    onTaskClick: (id: Long) -> Unit,
    onListClick: (id: Long) -> Unit,
    modifier: Modifier = Modifier,
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
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
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

            if (tasks.isNotEmpty()) {
                item(
                    span = StaggeredGridItemSpan.FullLine,
                    content = {

                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold),
                                    block = {
                                        append(stringResource(id = R.string.feature_search_tasks))
                                    }
                                )
                            },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                )
            }

            items(
                items = tasks,
                key = { it.hashCode() },
                contentType = { "Task" },
                itemContent = {

                    TaskSearchCard(
                        task = it,
                        onTaskClick = onTaskClick,
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .animateItem()
                    )
                }
            )

            if (lists.isNotEmpty()) {
                item(
                    span = StaggeredGridItemSpan.FullLine,
                    content = {

                        Text(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(fontWeight = FontWeight.Bold),
                                    block = {
                                        append(stringResource(id = R.string.feature_search_lists))
                                    }
                                )
                            },
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                    }
                )
            }

            items(
                items = lists,
                key = { it.hashCode() },
                contentType = { "List" },
                itemContent = {
                    ListSearchCard(
                        list = it,
                        onListClick = onListClick,
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
    HNotesTheme {
        HNotesBackground {
            SearchScreen(
                recentSearchQueriesUiState = SearchQueryUiState.Loading,
                searchResultUiState = SearchResultUiState.Loading,
                searchQuery = "",
                onSearchQueryChanged = {},
                onSearchTriggered = {},
                onClearRecentSearch = {},
                onClearAllRecentSearches = {},
                onBackClick = {},
                onNoteClick = {},
                onTaskClick = {},
                onListClick = {}
            )
        }
    }
}

@DevicePreviews
@Composable
private fun SearchScreenEmptyPreview() {
    HNotesTheme {
        HNotesBackground {
            SearchScreen(
                recentSearchQueriesUiState = SearchQueryUiState.Loading,
                searchResultUiState = SearchResultUiState.Success(searchResult = SearchResult()),
                searchQuery = "Hello World!",
                onSearchQueryChanged = {},
                onSearchTriggered = {},
                onClearRecentSearch = {},
                onClearAllRecentSearches = {},
                onBackClick = {},
                onNoteClick = {},
                onTaskClick = {},
                onListClick = {}
            )
        }
    }
}

@DevicePreviews
@Composable
private fun SearchScreenEmptyPreview(
    @PreviewParameter(SearchResultPreviewParameterProvider::class)
    searchResult: SearchResult,
) {
    HNotesTheme {
        HNotesBackground {
            SearchScreen(
                recentSearchQueriesUiState = SearchQueryUiState.Loading,
                searchResultUiState = SearchResultUiState.Success(
                    searchResult = searchResult.copy(
                        notes = searchResult.notes.filter {
                            it.title.contains("the") || it.description.contains("the")
                        },
                        tasks = searchResult.tasks.filter { it.title.contains("the") },
                        lists = searchResult.lists.filter {
                            it.title.contains("the")
                                    || it.items.all { item -> item.title.contains("the") }
                        }
                    )
                ),
                searchQuery = "the",
                onSearchQueryChanged = {},
                onSearchTriggered = {},
                onClearRecentSearch = {},
                onClearAllRecentSearches = {},
                onBackClick = {},
                onNoteClick = {},
                onTaskClick = {},
                onListClick = {}
            )
        }
    }
}

@DevicePreviews
@Composable
private fun SearchScreenPreview(
    @PreviewParameter(SearchQueryPreviewParameterProvider::class)
    searchQueries: KList<SearchQuery>,
) {
    HNotesTheme {
        HNotesBackground {
            SearchScreen(
                recentSearchQueriesUiState = SearchQueryUiState.Success(queries = searchQueries),
                searchResultUiState = SearchResultUiState.Loading,
                searchQuery = "",
                onSearchQueryChanged = {},
                onSearchTriggered = {},
                onClearRecentSearch = {},
                onClearAllRecentSearches = {},
                onBackClick = {},
                onNoteClick = {},
                onTaskClick = {},
                onListClick = {}
            )
        }
    }
}