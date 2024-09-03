package com.example.hnotes.feature.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import com.example.hnotes.core.model.data.Task
import com.example.hnotes.core.ui.DevicePreviews
import com.example.hnotes.core.ui.TaskCard
import com.example.hnotes.core.ui.TasksPreviewParameterProvider

@Composable
internal fun TasksRoute(
    navigateToTask: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TasksViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val selectedTasks by viewModel.selectedTasks.collectAsStateWithLifecycle()

    val deleteTasks by viewModel.deletedTasks.collectAsStateWithLifecycle()

    val isDeleteSuccess by viewModel.isDeleteSuccess.collectAsStateWithLifecycle()

    val multiSelectionEnabled by viewModel.multiSelectionEnabled.collectAsStateWithLifecycle()

    TasksScreen(
        uiState = uiState,
        selectedTasks = selectedTasks,
        onAllTasksSelectedChanged = viewModel::selectAllTasks,
        multiSelectionEnabled = multiSelectionEnabled,
        onMultiSelectionChanged = viewModel::updateMultiSelectionEnabled,
        taskSelected = { selectedTasks.contains(element = it) },
        onTaskSelectedChanged = viewModel::selectTask,
        onTaskClick = navigateToTask,
        deleteTasks = viewModel::deleteTasks,
        deletedTasks = deleteTasks,
        completeTasks = viewModel::completeTasks,
        isDeleteSuccess = isDeleteSuccess,
        restoreTasks = viewModel::restoreTasks,
        modifier = modifier
    )
}

@Composable
internal fun TasksScreen(
    uiState: TasksUiState,
    selectedTasks: List<Task>,
    onAllTasksSelectedChanged: (Boolean) -> Unit,
    multiSelectionEnabled: Boolean,
    onMultiSelectionChanged: (Boolean) -> Unit,
    taskSelected: (Task) -> Boolean,
    onTaskSelectedChanged: (Task) -> Unit,
    deleteTasks: () -> Unit,
    completeTasks: () -> Unit,
    onTaskClick: (Long) -> Unit,
    deletedTasks: List<Task>,
    isDeleteSuccess: Boolean,
    restoreTasks: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            when (uiState) {
                is TasksUiState.Loading -> {
                    TasksScreenLoading(modifier = modifier)
                }

                is TasksUiState.Success -> {
                    if (uiState.tasks.isNotEmpty()) {

                        TasksScreenContent(
                            uiState = uiState,
                            selectedTasks = selectedTasks,
                            onAllTasksSelectedChanged = onAllTasksSelectedChanged,
                            multiSelectionEnabled = multiSelectionEnabled,
                            onMultiSelectionChanged = onMultiSelectionChanged,
                            taskSelected = taskSelected,
                            onTaskSelectedChanged = onTaskSelectedChanged,
                            deleteTasks = deleteTasks,
                            completeTasks = completeTasks,
                            onTaskClick = onTaskClick,
                            deletedTasks = deletedTasks,
                            isDeleteSuccess = isDeleteSuccess,
                            restoreTasks = restoreTasks,
                            modifier = modifier
                        )
                    } else {
                        TasksScreenEmpty(
                            onTaskClick = onTaskClick,
                            modifier = modifier
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun TasksScreenLoading(modifier: Modifier = Modifier) {
    HNotesLoadingWheel(
        modifier = modifier.testTag(tag = "tasks::loading"),
        contentDescription = stringResource(id = R.string.feature_tasks_loading)
    )
}

@Composable
fun TasksScreenEmpty(
    onTaskClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(all = 16.dp)
            .testTag(tag = "tasks::empty"),
        verticalArrangement = Arrangement.spacedBy(
            space = 8.dp,
            alignment = Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {

            Text(
                text = stringResource(id = R.string.feature_tasks_empty_error),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
            )

            Text(
                text = stringResource(id = R.string.feature_tasks_empty_description),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )

            Button(
                onClick = { onTaskClick(-1) },
                content = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(
                            space = ButtonDefaults.IconSpacing,
                            alignment = Alignment.CenterHorizontally
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                        content = {
                            Icon(
                                imageVector = HNotesIcons.AddTask,
                                contentDescription = "Add Task",
                                modifier = Modifier.size(size = ButtonDefaults.IconSize)
                            )

                            Text(text = stringResource(id = R.string.feature_tasks_add_task))
                        }
                    )
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreenContent(
    uiState: TasksUiState.Success,
    selectedTasks: List<Task>,
    onAllTasksSelectedChanged: (Boolean) -> Unit,
    multiSelectionEnabled: Boolean,
    onMultiSelectionChanged: (Boolean) -> Unit,
    taskSelected: (Task) -> Boolean,
    onTaskSelectedChanged: (Task) -> Unit,
    deleteTasks: () -> Unit,
    completeTasks: () -> Unit,
    onTaskClick: (Long) -> Unit,
    deletedTasks: List<Task>,
    isDeleteSuccess: Boolean,
    restoreTasks: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val snackbarHostState = remember { SnackbarHostState() }

    val context = LocalContext.current

    val staggeredGridState = rememberLazyStaggeredGridState()

    val expandedFab by remember {
        derivedStateOf { staggeredGridState.firstVisibleItemIndex == 0 }
    }

    var deleteTasksResult by remember { mutableStateOf(value = false) }

    LaunchedEffect(key1 = deleteTasksResult) {
        if (deleteTasksResult) restoreTasks()
    }

    LaunchedEffect(key1 = isDeleteSuccess) {
        if (isDeleteSuccess) {
            deleteTasksResult = snackbarHostState.showSnackbar(
                message = context.resources.getQuantityString(
                    R.plurals.feature_tasks_removed_tasks,
                    deletedTasks.size,
                    deletedTasks.size
                ),
                actionLabel = context.resources.getString(R.string.feature_tasks_undo),
                duration = SnackbarDuration.Short
            ) == SnackbarResult.ActionPerformed
        }
    }

    val allTasksSelected = when {
        selectedTasks.isEmpty() -> ToggleableState.Off
        selectedTasks.containsAll(elements = uiState.tasks.values.flatten()) -> ToggleableState.On
        else -> ToggleableState.Indeterminate
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            if (multiSelectionEnabled) {
                HNotesTopAppBar(
                    title = {
                        Text(
                            text = if (selectedTasks.isEmpty())
                                stringResource(id = R.string.feature_tasks_no_selected_tasks)
                            else {
                                pluralStringResource(
                                    id = R.plurals.feature_tasks_selected_tasks,
                                    count = selectedTasks.size,
                                    selectedTasks.size
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
                                onAllTasksSelectedChanged(false)
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

                        HNotesIconButton(
                            onClick = completeTasks,
                            icon = {
                                Icon(
                                    imageVector = HNotesIcons.DoneAll,
                                    contentDescription = null
                                )
                            }
                        )

                        HNotesIconButton(
                            onClick = deleteTasks,
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
                onClick = { onTaskClick(-1) },
                expanded = expandedFab,
                icon = {
                    Icon(
                        imageVector = HNotesIcons.AddTask,
                        contentDescription = null
                    )
                },
                text = {

                    Text(text = stringResource(id = R.string.feature_tasks_add_task))
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
        containerColor = Color.Transparent,
        content = { paddingValues ->

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues)
                    .testTag(tag = "tasks::content"),
                content = {

                    if (multiSelectionEnabled) {

                        HNotesTriStateCheckbox(
                            state = allTasksSelected,
                            onClick = {
                                if (allTasksSelected == ToggleableState.On)
                                    onAllTasksSelectedChanged(false)

                                if (allTasksSelected == ToggleableState.Off)
                                    onAllTasksSelectedChanged(true)

                                if (allTasksSelected == ToggleableState.Indeterminate)
                                    onAllTasksSelectedChanged(true)
                            },
                            text = {
                                Text(
                                    text = stringResource(id = R.string.feature_tasks_select_all),
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

                            uiState.tasks.forEach { (isCompleted, tasks) ->

                                item(
                                    span = StaggeredGridItemSpan.FullLine,
                                    content = {
                                        Text(
                                            text = if (isCompleted)
                                                stringResource(id = R.string.feature_tasks_completed_group)
                                            else
                                                stringResource(id = R.string.feature_tasks_uncompleted_group),
                                            style = MaterialTheme.typography.titleMedium,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(horizontal = 8.dp)
                                                .animateItem()
                                        )
                                    }
                                )

                                items(
                                    items = tasks,
                                    key = { it.id },
                                    contentType = { it },
                                    itemContent = {

                                        TaskCard(
                                            task = it,
                                            multiSelectionEnabled = multiSelectionEnabled,
                                            enableMultiSelection = { onMultiSelectionChanged(true) },
                                            selected = taskSelected(it),
                                            onSelectedChanged = onTaskSelectedChanged,
                                            onTaskClick = onTaskClick,
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
fun NotesScreenLoadingPreview() {
    HNotesTheme {
        HNotesBackground {
            TasksScreen(
                uiState = TasksUiState.Loading,
                selectedTasks = emptyList(),
                onAllTasksSelectedChanged = {},
                multiSelectionEnabled = false,
                onMultiSelectionChanged = {},
                taskSelected = { false },
                onTaskSelectedChanged = {},
                deleteTasks = {},
                completeTasks = {},
                onTaskClick = {},
                deletedTasks = emptyList(),
                isDeleteSuccess = false,
                restoreTasks = {}
            )
        }
    }
}

@DevicePreviews
@Composable
fun NotesScreenPopulatedPreview(
    @PreviewParameter(TasksPreviewParameterProvider::class) tasks: Map<Boolean, List<Task>>,
) {
    HNotesTheme {
        HNotesBackground {
            TasksScreen(
                uiState = TasksUiState.Success(tasks = tasks),
                selectedTasks = tasks.values.flatten().filter(Task::isCompleted),
                onAllTasksSelectedChanged = {},
                multiSelectionEnabled = true,
                onMultiSelectionChanged = {},
                taskSelected = { tasks.values.flatten().filter(Task::isCompleted).contains(it) },
                onTaskSelectedChanged = {},
                deleteTasks = {},
                completeTasks = {},
                onTaskClick = {},
                deletedTasks = emptyList(),
                isDeleteSuccess = false,
                restoreTasks = {}
            )
        }
    }
}

@DevicePreviews
@Composable
fun NotesScreenEmptyPreview() {
    HNotesTheme {
        HNotesBackground {
            TasksScreen(
                uiState = TasksUiState.Success(tasks = emptyMap()),
                selectedTasks = emptyList(),
                onAllTasksSelectedChanged = {},
                multiSelectionEnabled = false,
                onMultiSelectionChanged = {},
                taskSelected = { false },
                onTaskSelectedChanged = {},
                deleteTasks = {},
                completeTasks = {},
                onTaskClick = {},
                deletedTasks = emptyList(),
                isDeleteSuccess = false,
                restoreTasks = {}
            )
        }
    }
}

