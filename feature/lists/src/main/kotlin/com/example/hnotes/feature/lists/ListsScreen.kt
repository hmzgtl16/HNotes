package com.example.hnotes.feature.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnotes.core.designsystem.component.HNotesBackground
import com.example.hnotes.core.designsystem.component.HNotesIconButton
import com.example.hnotes.core.designsystem.component.HNotesLoadingWheel
import com.example.hnotes.core.designsystem.component.HNotesTopAppBar
import com.example.hnotes.core.designsystem.component.HNotesTriStateCheckbox
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.model.data.List
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.ListCard
import com.example.hnotes.core.ui.ListsPreviewParameterProvider
import kotlin.collections.List as KList

@Composable
internal fun ListsRoute(
    navigateToList: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ListsViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val selectedLists by viewModel.selectedLists.collectAsStateWithLifecycle()

    val deleteLists by viewModel.deletedLists.collectAsStateWithLifecycle()

    val isDeleteSuccess by viewModel.isDeleteSuccess.collectAsStateWithLifecycle()

    val multiSelectionEnabled by viewModel.multiSelectionEnabled.collectAsStateWithLifecycle()

    ListsScreen(
        uiState = uiState,
        selectedLists = selectedLists,
        onAllListsSelectedChanged = viewModel::onSelectAllListsChecked,
        multiSelectionEnabled = multiSelectionEnabled,
        onMultiSelectionChanged = viewModel::onMultiSelectionChanged,
        listSelected = selectedLists::contains,
        onListSelectedChanged = viewModel::onListSelectedChanged,
        deleteLists = viewModel::deleteLists,
        pinLists = viewModel::pinLists,
        onListClick = navigateToList,
        onPinClick = viewModel::pinList,
        deletedLists = deleteLists,
        isDeleteSuccess = isDeleteSuccess,
        restoreLists = viewModel::restoreLists,
        modifier = modifier
    )
}

@Composable
internal fun ListsScreen(
    uiState: ListsUiState,
    selectedLists: KList<List>,
    onAllListsSelectedChanged: (Boolean) -> Unit,
    multiSelectionEnabled: Boolean,
    onMultiSelectionChanged: (Boolean) -> Unit,
    listSelected: (List) -> Boolean,
    onListSelectedChanged: (List) -> Unit,
    deleteLists: () -> Unit,
    pinLists: (Boolean) -> Unit,
    onListClick: (Long) -> Unit,
    onPinClick: (List) -> Unit,
    deletedLists: KList<List>,
    isDeleteSuccess: Boolean,
    restoreLists: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            when (uiState) {
                ListsUiState.Loading -> {
                    ListsScreenLoading(modifier = modifier)
                }

                is ListsUiState.Success -> {
                    if (uiState.lists.isNotEmpty()) {

                        ListsScreenContent(
                            uiState = uiState,
                            selectedLists = selectedLists,
                            onAllListsSelectedChanged = onAllListsSelectedChanged,
                            multiSelectionEnabled = multiSelectionEnabled,
                            onMultiSelectionChanged = onMultiSelectionChanged,
                            listSelected = listSelected,
                            onListSelectedChanged = onListSelectedChanged,
                            deleteLists = deleteLists,
                            pinLists = pinLists,
                            onListClick = onListClick,
                            onPinClick = onPinClick,
                            deletedLists = deletedLists,
                            isDeleteSuccess = isDeleteSuccess,
                            restoreLists = restoreLists,
                            modifier = modifier
                        )
                    } else {
                        ListsScreenEmpty(
                            onListClick = onListClick,
                            modifier = modifier
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun ListsScreenLoading(
    modifier: Modifier = Modifier,
) {
    HNotesLoadingWheel(
        modifier = modifier.testTag(tag = "lists::loading"),
        contentDescription = stringResource(id = R.string.feature_lists_loading)
    )
}

@Composable
fun ListsScreenEmpty(
    onListClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .testTag(tag = "lists::empty"),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            Text(
                text = stringResource(id = R.string.feature_lists_empty_error),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(id = R.string.feature_lists_empty_description),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )

            Button(
                onClick = { onListClick(-1) },
                content = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            space = ButtonDefaults.IconSpacing,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                imageVector = HNotesIcons.AddNote,
                                contentDescription = "Add Note",
                                modifier = Modifier.size(size = ButtonDefaults.IconSize)
                            )

                            Text(text = stringResource(id = R.string.feature_lists_add_list))
                        }
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListsScreenContent(
    uiState: ListsUiState.Success,
    selectedLists: KList<List>,
    onAllListsSelectedChanged: (Boolean) -> Unit,
    multiSelectionEnabled: Boolean,
    onMultiSelectionChanged: (Boolean) -> Unit,
    listSelected: (List) -> Boolean,
    onListSelectedChanged: (List) -> Unit,
    deleteLists: () -> Unit,
    pinLists: (Boolean) -> Unit,
    onListClick: (Long) -> Unit,
    onPinClick: (List) -> Unit,
    deletedLists: KList<List>,
    isDeleteSuccess: Boolean,
    restoreLists: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current

    val staggeredGridState = rememberLazyStaggeredGridState()

    val snackbarHostState = remember { SnackbarHostState() }

    val expandedFab by remember {
        derivedStateOf { staggeredGridState.firstVisibleItemIndex == 0 }
    }

    var deleteListsResult by remember { mutableStateOf(value = false) }

    LaunchedEffect(key1 = deleteListsResult) {
        if (deleteListsResult) restoreLists()
    }

    LaunchedEffect(key1 = isDeleteSuccess) {
        if (isDeleteSuccess) {
            deleteListsResult = snackbarHostState.showSnackbar(
                message = context.resources.getQuantityString(
                    R.plurals.feature_lists_removed_lists,
                    deletedLists.size,
                    deletedLists.size
                ),
                actionLabel = context.resources.getString(R.string.feature_lists_undo),
                duration = SnackbarDuration.Short
            ) == SnackbarResult.ActionPerformed
        }
    }

    val allListsSelected = when {
        selectedLists.isEmpty() -> ToggleableState.Off
        selectedLists.containsAll(elements = uiState.lists.values.flatten()) -> ToggleableState.On
        else -> ToggleableState.Indeterminate
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (multiSelectionEnabled) {
                HNotesTopAppBar(
                    title = {
                        Text(
                            text = if (selectedLists.isEmpty())
                                stringResource(id = R.string.feature_lists_no_selected_lists)
                            else {
                                pluralStringResource(
                                    id = R.plurals.feature_lists_selected_lists,
                                    count = selectedLists.size,
                                    selectedLists.size
                                )
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        HNotesIconButton(
                            onClick = {
                                onMultiSelectionChanged(false)
                                onAllListsSelectedChanged(false)
                            },
                            icon = {
                                Icon(
                                    imageVector = HNotesIcons.Close,
                                    contentDescription = null
                                )
                            }
                        )
                    },
                    actions = {

                        val mostPinned = selectedLists.all(predicate = List::isPinned)

                        HNotesIconButton(
                            onClick = { pinLists(!mostPinned) },
                            icon = {
                                Icon(
                                    imageVector = if (mostPinned) HNotesIcons.PinBorder else HNotesIcons.Pin,
                                    contentDescription = null
                                )
                            }
                        )

                        HNotesIconButton(
                            onClick = deleteLists,
                            icon = {
                                Icon(
                                    imageVector = HNotesIcons.Delete,
                                    contentDescription = null
                                )
                            }
                        )
                    },
                    isCenterAligned = false,
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            }
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onListClick(-1) },
                expanded = expandedFab,
                icon = {
                    Icon(
                        imageVector = HNotesIcons.AddList,
                        contentDescription = null
                    )
                },
                text = {
                    Text(text = stringResource(id = R.string.feature_lists_add_list))
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = Color.Transparent,
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .testTag(tag = "lists::content"),
                content = {

                    if (multiSelectionEnabled) {

                        HNotesTriStateCheckbox(
                            state = allListsSelected,
                            onClick = {
                                if (allListsSelected == ToggleableState.On)
                                    onAllListsSelectedChanged(false)

                                if (allListsSelected == ToggleableState.Off)
                                    onAllListsSelectedChanged(true)

                                if (allListsSelected == ToggleableState.Indeterminate)
                                    onAllListsSelectedChanged(true)
                            },
                            text = {
                                Text(
                                    text = stringResource(id = R.string.feature_lists_select_all),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                            },
                            modifier = Modifier.wrapContentWidth()
                        )
                    }

                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Adaptive(minSize = 250.dp),
                        contentPadding = PaddingValues(all = 16.dp),
                        verticalItemSpacing = 16.dp,
                        state = staggeredGridState,
                        content = {

                            uiState.lists.forEach { (isPinned, lists) ->

                                if (isPinned) {
                                    item(
                                        span = StaggeredGridItemSpan.FullLine,
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.feature_lists_pinned_group),
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 8.dp)
                                                    .animateItem()
                                            )
                                        }
                                    )
                                }

                                if (!isPinned && lists.isNotEmpty()
                                    && uiState.lists.getValue(key = true).isNotEmpty()) {
                                    item(
                                        span = StaggeredGridItemSpan.FullLine,
                                        content = {
                                            Text(
                                                text = stringResource(id = R.string.feature_lists_unpinned_group),
                                                style = MaterialTheme.typography.titleMedium,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 8.dp)
                                                    .animateItem()
                                            )
                                        }
                                    )
                                }

                                items(
                                    items = lists,
                                    key = { it.id },
                                    contentType = { it },
                                    itemContent = {

                                        ListCard(
                                            list = it,
                                            multiSelectionEnabled = multiSelectionEnabled,
                                            enableMultiSelection = { onMultiSelectionChanged(true) },
                                            selected = listSelected(it),
                                            onSelectedChanged = onListSelectedChanged,
                                            onPinClick = onPinClick,
                                            onListClick = onListClick,
                                            modifier = Modifier
                                                .padding(horizontal = 8.dp)
                                                .animateItem()
                                        )
                                    }
                                )
                            }
                        }
                    )
                }
            )
        }
    )
}

@DevicePreviews
@Composable
fun ListsScreenLoadingPreview() {
    HNotesTheme {
        HNotesBackground {
            ListsScreen(
                uiState = ListsUiState.Loading,
                selectedLists = emptyList(),
                onAllListsSelectedChanged = {},
                multiSelectionEnabled = false,
                onMultiSelectionChanged = {},
                listSelected = { false },
                onListSelectedChanged = {},
                deleteLists = {},
                pinLists = {},
                onListClick = {},
                onPinClick = {},
                deletedLists = emptyList(),
                isDeleteSuccess = false,
                restoreLists = {}
            )
        }
    }
}

@DevicePreviews
@Composable
fun ListsScreenEmptyPreview() {
    HNotesTheme {
        HNotesBackground {
            ListsScreen(
                uiState = ListsUiState.Success(lists = emptyMap()),
                selectedLists = emptyList(),
                onAllListsSelectedChanged = {},
                multiSelectionEnabled = false,
                onMultiSelectionChanged = {},
                listSelected = { false },
                onListSelectedChanged = {},
                deleteLists = {},
                pinLists = {},
                onListClick = {},
                onPinClick = {},
                deletedLists = emptyList(),
                isDeleteSuccess = false,
                restoreLists = {}
            )
        }
    }
}

@DevicePreviews
@Composable
fun ListsScreenContentPreview(
    @PreviewParameter(ListsPreviewParameterProvider::class)
    lists: Map<Boolean, KList<List>>
) {
    HNotesTheme {
        HNotesBackground {
            ListsScreen(
                uiState = ListsUiState.Success(lists = lists),
                selectedLists = emptyList(),
                onAllListsSelectedChanged = {},
                multiSelectionEnabled = true,
                onMultiSelectionChanged = {},
                listSelected = { false },
                onListSelectedChanged = {},
                deleteLists = {},
                pinLists = {},
                onListClick = {},
                onPinClick = {},
                deletedLists = emptyList(),
                isDeleteSuccess = false,
                restoreLists = {}
            )
        }
    }
}

@DevicePreviews
@Composable
fun ListsScreenContentMultiSelectionEnabledPreview(
    @PreviewParameter(ListsPreviewParameterProvider::class)
    lists: Map<Boolean, KList<List>>,
) {
    HNotesTheme {
        HNotesBackground {
            ListsScreen(
                uiState = ListsUiState.Success(lists = lists),
                selectedLists = lists.values.flatten().filter(List::isPinned),
                onAllListsSelectedChanged = {},
                multiSelectionEnabled = true,
                onMultiSelectionChanged = {},
                listSelected = { lists.values.flatten().filter(List::isPinned).contains(it) },
                onListSelectedChanged = {},
                deleteLists = {},
                pinLists = {},
                onListClick = {},
                onPinClick = {},
                deletedLists = emptyList(),
                isDeleteSuccess = false,
                restoreLists = {}
            )
        }
    }
}

