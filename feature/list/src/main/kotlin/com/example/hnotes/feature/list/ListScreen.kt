package com.example.hnotes.feature.list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.hnotes.core.designsystem.component.HNotesAlertDialog
import com.example.hnotes.core.designsystem.component.HNotesBackground
import com.example.hnotes.core.designsystem.component.HNotesIconButton
import com.example.hnotes.core.designsystem.component.HNotesIconToggleButton
import com.example.hnotes.core.designsystem.component.HNotesOutlinedTextField
import com.example.hnotes.core.designsystem.component.HNotesTextButton
import com.example.hnotes.core.designsystem.component.HNotesTopAppBar
import com.example.hnotes.core.designsystem.icon.HNotesIcons
import com.example.hnotes.core.designsystem.theme.HNotesTheme
import com.example.hnotes.core.model.data.Item
import com.example.hnotes.core.model.data.List
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.ItemCard
import com.example.hnotes.core.ui.ListsPreviewParameterProvider
import com.example.hnotes.core.ui.PaletteModalBottomSheet
import com.example.hnotes.core.ui.format
import kotlinx.datetime.Instant
import kotlin.collections.List as KList

@Composable
internal fun ListRoute(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
) {

    val list by viewModel.list.collectAsStateWithLifecycle()
    val title by viewModel.title.collectAsStateWithLifecycle()
    val items by viewModel.items.collectAsStateWithLifecycle()
    val newItemTitle by viewModel.newItemTitle.collectAsStateWithLifecycle()
    val backgroundColor by viewModel.backgroundColor.collectAsStateWithLifecycle()
    val pinned by viewModel.pinned.collectAsStateWithLifecycle()
    val paletteVisibility by viewModel.paletteVisibility.collectAsStateWithLifecycle()
    val deleteDialogVisibility by viewModel.deleteDialogVisibility.collectAsStateWithLifecycle()
    val isDeleted by viewModel.isDeleted.collectAsStateWithLifecycle()

    BackHandler(
        onBack = {
            viewModel.saveList()
            navigateBack()
        }
    )

    LaunchedEffect(key1 = isDeleted) {
        if (list == null && isDeleted) navigateBack()
    }

    ListScreen(
        title = title,
        onTitleChange = viewModel::onTitleChange,
        items = items.groupBy(Item::isCompleted).toSortedMap(),
        onAddNewItem = viewModel::onAddNewItem,
        onItemTitleChange = viewModel::onItemTitleChange,
        newItemTitle = newItemTitle,
        onNewItemTitleChange = viewModel::onNewItemTitleChange,
        backgroundColor = backgroundColor,
        onBackgroundColorChange = viewModel::onBackgroundColorChange,
        pinned = pinned,
        onPinnedChange = viewModel::onPinnedChange,
        lastEdit = list?.updated,
        paletteVisibility = paletteVisibility,
        onPaletteVisibilityChange = viewModel::onPaletteVisibilityChange,
        onItemCompletedChange = viewModel::completeItem,
        deleteDialogVisibility = deleteDialogVisibility,
        onDeleteDialogVisibilityChange = viewModel::onDeleteDialogVisibilityChange,
        onCopyClick = viewModel::copyList,
        onDeleteClick = viewModel::deleteList,
        onDeleteItemClick = viewModel::deleteItem,
        onBackClick = {
            viewModel.saveList()
            navigateBack()
        },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ListScreen(
    title: String,
    onTitleChange: (title: String) -> Unit,
    items: Map<Boolean, KList<Item>>,
    onAddNewItem: () -> Unit,
    onItemTitleChange: (item: Item, title: String) -> Unit,
    newItemTitle: String,
    onNewItemTitleChange: (title: String) -> Unit,
    backgroundColor: Int?,
    onBackgroundColorChange: (color: Int?) -> Unit,
    pinned: Boolean,
    onPinnedChange: (isPinned: Boolean) -> Unit,
    lastEdit: Instant?,
    paletteVisibility: Boolean,
    onPaletteVisibilityChange: (Boolean) -> Unit,
    onItemCompletedChange: (item: Item) -> Unit,
    deleteDialogVisibility: Boolean,
    onDeleteDialogVisibilityChange: (isVisible: Boolean) -> Unit,
    onCopyClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDeleteItemClick: (item: Item) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val focusManager = LocalFocusManager.current

    val paletteModalBottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = if (backgroundColor != null) Color(color = backgroundColor)
        else MaterialTheme.colorScheme.background,
        topBar = {
            HNotesTopAppBar(
                title = {},
                navigationIcon = {
                    HNotesIconButton(
                        onClick = onBackClick,
                        icon = {
                            Icon(
                                imageVector = HNotesIcons.Back,
                                contentDescription = null
                            )
                        }
                    )
                },
                actions = {

                    HNotesIconToggleButton(
                        checked = pinned,
                        onCheckedChange = { onPinnedChange(it) },
                        icon = {
                            Icon(
                                imageVector = HNotesIcons.PinBorder,
                                contentDescription = null
                            )
                        },
                        checkedIcon = {
                            Icon(
                                imageVector = HNotesIcons.Pin,
                                contentDescription = null
                            )
                        }
                    )

                    HNotesIconButton(
                        onClick = onCopyClick,
                        icon = {
                            Icon(
                                imageVector = HNotesIcons.Copy,
                                contentDescription = null,
                            )
                        }
                    )

                    HNotesIconButton(
                        onClick = { onDeleteDialogVisibilityChange(true) },
                        icon = {
                            Icon(
                                imageVector = HNotesIcons.Delete,
                                contentDescription = null,
                            )
                        }
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent)
            )
        },
        content = { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = 24.dp,
                    alignment = Alignment.Top
                ),
                content = {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(weight = 1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = 16.dp,
                            alignment = Alignment.Top
                        ),
                        content = {

                            HNotesOutlinedTextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = title,
                                onValueChange = onTitleChange,
                                placeholder = {
                                    Text(text = stringResource(id = R.string.feature_list_title_placeholder))
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    capitalization = KeyboardCapitalization.Words,
                                    autoCorrectEnabled = true,
                                    keyboardType = KeyboardType.Text,
                                    imeAction = ImeAction.Next
                                ),
                                keyboardActions = KeyboardActions(
                                    onNext = {
                                        focusManager.moveFocus(focusDirection = FocusDirection.Next)
                                    }
                                )
                            )

                            LazyColumn(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(weight = 1f),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top,
                                content = {

                                    items[false]?.let { uncompletedItems ->

                                        items(
                                            items = uncompletedItems,
                                            contentType = { it },
                                            itemContent = { item ->

                                                ItemCard(
                                                    item = item,
                                                    onItemTitleChange = {
                                                        onItemTitleChange(item, it)
                                                    },
                                                    onItemCompletedChange = {
                                                        onItemCompletedChange(item)
                                                    },
                                                    onDeleteItemClick = {
                                                        onDeleteItemClick(item)
                                                    },
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            }
                                        )
                                    }

                                    item {

                                        HNotesOutlinedTextField(
                                            modifier = Modifier.fillMaxWidth(),
                                            value = newItemTitle,
                                            onValueChange = onNewItemTitleChange,
                                            placeholder = {
                                                Text(text = stringResource(id = R.string.feature_list_add_item_placeholder))
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    imageVector = HNotesIcons.Add,
                                                    contentDescription = null
                                                )
                                            },
                                            keyboardOptions = KeyboardOptions.Default.copy(
                                                capitalization = KeyboardCapitalization.Sentences,
                                                autoCorrectEnabled = true,
                                                keyboardType = KeyboardType.Text,
                                                imeAction = ImeAction.Go
                                            ),
                                            keyboardActions = KeyboardActions(
                                                onGo = { onAddNewItem() }
                                            )
                                        )

                                        HorizontalDivider()
                                    }

                                    items[true]?.let { completedItems ->

                                        item {

                                            Text(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(
                                                        start = 16.dp,
                                                        top = 16.dp,
                                                        end = 16.dp
                                                    ),
                                                text = pluralStringResource(
                                                    id = R.plurals.feature_list_completed_items,
                                                    count = completedItems.size,
                                                    completedItems.size
                                                )
                                            )
                                        }

                                        items(
                                            items = completedItems,
                                            contentType = { it },
                                            itemContent = { item ->

                                                ItemCard(
                                                    item = item,
                                                    onItemTitleChange = {
                                                        onItemTitleChange(item, it)
                                                    },
                                                    onDeleteItemClick = { onDeleteItemClick(item) },
                                                    onItemCompletedChange = { onItemCompletedChange(item) },
                                                    modifier = Modifier.fillMaxWidth()
                                                )
                                            }
                                        )
                                    }
                                }
                            )

                            if (lastEdit != null)
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = stringResource(
                                        id = R.string.feature_list_last_edit,
                                        lastEdit.format()
                                    ),
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.labelSmall,
                                )
                        }
                    )
                }
            )

            if (deleteDialogVisibility) {
                HNotesAlertDialog(
                    onDismissRequest = {
                        onDeleteDialogVisibilityChange(false)
                    },
                    confirmButton = {
                        HNotesTextButton(
                            onClick = {
                                onDeleteDialogVisibilityChange(false)
                                onDeleteClick()
                            },
                            text = {
                                Text(
                                    text = stringResource(R.string.feature_list_delete_list_confirm),
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    },
                    dismissButton = {
                        HNotesTextButton(
                            onClick = {
                                onDeleteDialogVisibilityChange(false)
                            },
                            text = {
                                Text(
                                    text = stringResource(R.string.feature_list_delete_list_dismiss),
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                        )
                    },
                    title = {
                        Text(
                            text = stringResource(
                                id = R.string.feature_list_delete_list_title,
                                title.ifEmpty {
                                    stringResource(id = R.string.feature_list_title_unspecified)
                                }
                            ),
                            style = MaterialTheme.typography.titleLarge,
                        )
                    },
                    text = {
                        Text(
                            text = stringResource(R.string.feature_list_delete_list_description),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                )
            }

            if (paletteVisibility) {
                PaletteModalBottomSheet(
                    sheetState = paletteModalBottomSheetState,
                    onDismissRequest = { onPaletteVisibilityChange(false) },
                    backgroundColor = backgroundColor,
                    onBackgroundColorChange = {
                        onBackgroundColorChange(it)
                        onPaletteVisibilityChange(false)
                    }
                )
            }
        }
    )
}

@DevicePreviews
@Composable
fun NoteScreenPreview(
    @PreviewParameter(ListsPreviewParameterProvider::class)
    lists: Map<Boolean, KList<List>>,
) {
    HNotesTheme {
        HNotesBackground {
            ListScreen(
                title = lists[true]!![0].title,
                onTitleChange = {},
                items = lists[true]!![0].items.groupBy(Item::isCompleted).toSortedMap(),
                onAddNewItem = {},
                onItemTitleChange = { _, _ -> },
                newItemTitle = "",
                onNewItemTitleChange = {},
                backgroundColor = null,
                onBackgroundColorChange = {},
                pinned = lists[true]!![0].isPinned,
                onPinnedChange = {},
                lastEdit = lists[true]!![0].updated,
                paletteVisibility = false,
                onPaletteVisibilityChange = {},
                onItemCompletedChange = {},
                deleteDialogVisibility = false,
                onDeleteDialogVisibilityChange = {},
                onCopyClick = {},
                onDeleteClick = {},
                onDeleteItemClick = {},
                onBackClick = {}
            )
        }
    }
}
