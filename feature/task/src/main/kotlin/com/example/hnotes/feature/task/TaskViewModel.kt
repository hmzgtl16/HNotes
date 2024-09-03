package com.example.hnotes.feature.task

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.hnotes.core.data.repository.TaskRepository
import com.example.hnotes.core.model.data.RepeatMode
import com.example.hnotes.core.model.data.Task
import com.example.hnotes.core.ui.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val taskRepository: TaskRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        val id = savedStateHandle.toRoute<Route.TaskRoute>().id

        viewModelScope.launch {
            taskRepository.getTask(id = id).collect {
                _task.value = it
                _title.value = it?.title ?: ""
                _reminder.value = it?.reminder
                _repeatMode.value = it?.repeatMode ?: RepeatMode.NONE
                _isEdited.value = false
            }
        }
    }

    private val _task = MutableStateFlow<Task?>(value = null)
    val task: StateFlow<Task?> get() = _task

    private val _title = MutableStateFlow(value = "")
    val title: StateFlow<String> get() = _title

    private val _reminder = MutableStateFlow<Instant?>(value = null)
    val reminder: StateFlow<Instant?> get() = _reminder

    private val _repeatMode = MutableStateFlow(value = RepeatMode.NONE)
    val repeatMode: StateFlow<RepeatMode> get() = _repeatMode

    private val _reminderPickerVisibility = MutableStateFlow(value = false)
    val reminderPickerVisibility: StateFlow<Boolean> get() = _reminderPickerVisibility

    private val _isEdited = MutableStateFlow(value = false)
    val isEdited: StateFlow<Boolean> get() = _isEdited

    fun onTitleChange(value: String) {
        _isEdited.value = true
        _title.value = value
    }

    fun onReminderChange(value: Instant) {
        _isEdited.value = true
        _reminder.value = value
    }

    fun onClearReminder() {
        _isEdited.value = true
        _reminder.value = null
    }

    fun onRepeatModeChange(value: RepeatMode) {
        _isEdited.value = true
        _repeatMode.value = value
    }

    fun onReminderPickerVisibilityChange(value: Boolean) {
        _reminderPickerVisibility.value = value
    }

    fun saveTask() = viewModelScope.launch {
        if (!isEdited.value) return@launch
        val task = task.value ?: Task()
        taskRepository.updateTask(
            task = task.copy(
                title = title.value,
                updated = Clock.System.now(),
                reminder = reminder.value!!,
                repeatMode = repeatMode.value,
            )
        )
        _isEdited.value = false
    }

    fun completeTask() = viewModelScope.launch {
        val task = task.value ?: return@launch
        taskRepository.updateTask(
            task = task.copy(
                isCompleted = true,
                updated = Clock.System.now()
            )
        )
        _isEdited.value = false

    }
}

