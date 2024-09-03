package com.example.hnotes.feature.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.TaskRepository
import com.example.hnotes.core.domain.usecase.GetTasksUseCase
import com.example.hnotes.core.model.data.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    getTasksUseCase: GetTasksUseCase
) : ViewModel() {

    val uiState: StateFlow<TasksUiState> = getTasksUseCase
        .invoke()
        .map(TasksUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = TasksUiState.Loading
        )

    private val _isDeleteSuccess = MutableStateFlow(value = false)
    val isDeleteSuccess: StateFlow<Boolean> get() = _isDeleteSuccess

    private val _deletedTasks = MutableStateFlow(value = emptyList<Task>())
    val deletedTasks: StateFlow<List<Task>> get() = _deletedTasks

    private val _selectedTasks = MutableStateFlow(value = emptyList<Task>())
    val selectedTasks: StateFlow<List<Task>> get() = _selectedTasks

    private val _multiSelectionEnabled = MutableStateFlow(value = false)
    val multiSelectionEnabled: StateFlow<Boolean> get() = _multiSelectionEnabled

    fun updateMultiSelectionEnabled(value: Boolean) = _multiSelectionEnabled.update { value }

    fun selectTask(task: Task) = _selectedTasks.update {
        val modified = it.toMutableList()

        if (modified.contains(element = task)) modified.remove(element = task)
        else modified.add(element = task)

        modified
    }

    fun selectAllTasks(value: Boolean) {
        if (value) _selectedTasks.value =
            (uiState.value as TasksUiState.Success).tasks.values.flatten()
        else _selectedTasks.value = emptyList()
    }

    fun deleteTasks() = viewModelScope.launch {
        _isDeleteSuccess.value = taskRepository.deleteTasks(tasks = selectedTasks.value).isSuccess
        _deletedTasks.value = selectedTasks.value
        _selectedTasks.value = emptyList()
        _multiSelectionEnabled.value = false
    }

    fun restoreTasks() = viewModelScope.launch {
        taskRepository.updateTasks(tasks = deletedTasks.value)
        _deletedTasks.value = emptyList()
        _isDeleteSuccess.value = false
    }

    fun completeTasks() = viewModelScope.launch {
        val tasks = selectedTasks.value
            .filterNot(Task::isCompleted)
            .map { it.copy(isCompleted = true) }
        taskRepository.updateTasks(tasks = tasks)
        _selectedTasks.value = emptyList()
        _multiSelectionEnabled.value = false
    }
}

sealed interface TasksUiState {

    data object Loading : TasksUiState
    data class Success(val tasks: Map<Boolean, List<Task>>) : TasksUiState
}
