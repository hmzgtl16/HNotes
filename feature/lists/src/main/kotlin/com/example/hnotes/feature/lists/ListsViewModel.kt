package com.example.hnotes.feature.lists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hnotes.core.data.repository.ListRepository
import com.example.hnotes.core.domain.usecase.GetListsUseCase
import com.example.hnotes.core.model.data.List
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.List as KList

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val listRepository: ListRepository,
    getListsUseCase: GetListsUseCase
) : ViewModel() {

    val uiState: StateFlow<ListsUiState> = getListsUseCase
        .invoke()
        .map(ListsUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000L),
            initialValue = ListsUiState.Loading
        )

    private val _isDeleteSuccess = MutableStateFlow(value = false)
    val isDeleteSuccess: StateFlow<Boolean> get() = _isDeleteSuccess

    private val _deletedLists = MutableStateFlow(value = emptyList<List>())
    val deletedLists: StateFlow<KList<List>> get() = _deletedLists

    private val _selectedLists = MutableStateFlow(value = emptyList<List>())
    val selectedLists: StateFlow<KList<List>> get() = _selectedLists

    private val _multiSelectionEnabled = MutableStateFlow(value = false)
    val multiSelectionEnabled: StateFlow<Boolean> get() = _multiSelectionEnabled

    fun onMultiSelectionChanged(value: Boolean) = _multiSelectionEnabled.update { value }

    fun onListSelectedChanged(list: List) = _selectedLists.update {
        val modified = it.toMutableList()

        if (modified.contains(element = list)) modified.remove(element = list)
        else modified.add(element = list)

        modified
    }

    fun onSelectAllListsChecked(value: Boolean) {
        if (value) _selectedLists.value = (uiState.value as ListsUiState.Success).lists.values.flatten()
        else _selectedLists.value = emptyList()
    }

    fun deleteLists() = viewModelScope.launch {
        _isDeleteSuccess.value = listRepository.deleteLists(lists = selectedLists.value).isSuccess
        _deletedLists.value = selectedLists.value
        _selectedLists.value = emptyList()
        _multiSelectionEnabled.value = false
    }

    fun restoreLists() = viewModelScope.launch {
        listRepository.updateLists(lists = deletedLists.value)
        _deletedLists.value = emptyList()
        _isDeleteSuccess.value = false
    }

    fun pinList(list: List) = viewModelScope.launch {
        listRepository.updateList(list = list.copy(isPinned = !list.isPinned))
    }

    fun pinLists(isPinned: Boolean) = viewModelScope.launch {
        listRepository.updateLists(lists = selectedLists.value.map { it.copy(isPinned = isPinned) })
        _selectedLists.value = emptyList()
        _multiSelectionEnabled.value = false
    }
}

sealed interface ListsUiState {

    data object Loading : ListsUiState

    data class Success(val lists: Map<Boolean, KList<List>>) : ListsUiState
}
