package com.example.hnotes.feature.list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.hnotes.core.data.repository.ListRepository
import com.example.hnotes.core.model.data.Item
import com.example.hnotes.core.model.data.List
import com.example.hnotes.core.ui.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlin.collections.List as KList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val listRepository: ListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        val id = savedStateHandle.toRoute<Route.ListRoute>().id

        viewModelScope.launch {
            listRepository.getList(id = id).collect {
                _list.value = it
                _title.value = it?.title ?: ""
                _items.value = it?.items ?: emptyList()
                _backgroundColor.value = it?.backgroundColor
                _pinned.value = it?.isPinned ?: false
                _isEdited.value = false
            }
        }
    }

    private val _list = MutableStateFlow<List?>(value = null)
    val list: StateFlow<List?> get() = _list

    private val _title = MutableStateFlow(value = "")
    val title: StateFlow<String> get() = _title

    private val _items = MutableStateFlow<KList<Item>>(value = emptyList())
    val items: StateFlow<KList<Item>> get() = _items

    private val _newItemTitle = MutableStateFlow(value = "")
    val newItemTitle: StateFlow<String> get() = _newItemTitle

    private val _backgroundColor = MutableStateFlow<Int?>(value = null)
    val backgroundColor: StateFlow<Int?> get() = _backgroundColor

    private val _pinned = MutableStateFlow(value = false)
    val pinned: StateFlow<Boolean> get() = _pinned

    private val _paletteVisibility = MutableStateFlow(value = false)
    val paletteVisibility: StateFlow<Boolean> get() = _paletteVisibility

    private val _deleteDialogVisibility = MutableStateFlow(value = false)
    val deleteDialogVisibility: StateFlow<Boolean> get() = _deleteDialogVisibility

    private val _isEdited = MutableStateFlow(value = false)
    private val isEdited: StateFlow<Boolean> get() = _isEdited

    private val _isDeleted = MutableStateFlow(value = false)
    val isDeleted: StateFlow<Boolean> get() = _isDeleted

    fun onTitleChange(value: String) {
        _isEdited.value = true
        _title.value = value
    }

    fun onItemTitleChange(item: Item, title: String) {
        val index = items.value.indexOfFirst { it === item }
        _items.update {
            val modified = it.toMutableList()
            modified[index] = modified[index].copy(title = title)

            modified
        }
        _isEdited.value = true
    }

    fun onNewItemTitleChange(value: String) {
        _newItemTitle.value = value
    }

    fun onAddNewItem() {
        if (newItemTitle.value.isBlank()) return
        _isEdited.value = true
        _items.update {
            val modified = it.toMutableList()
            modified.add(element = Item(title = newItemTitle.value))

            modified
        }
        _newItemTitle.value = ""
    }

    fun onBackgroundColorChange(value: Int?) {
        _isEdited.value = true
        _backgroundColor.value = value
    }

    fun onPinnedChange(value: Boolean) {
        _isEdited.value = true
        _pinned.value = value
    }

    fun onPaletteVisibilityChange(value: Boolean) {
        _paletteVisibility.value = value
    }

    fun onDeleteDialogVisibilityChange(value: Boolean) {
        _deleteDialogVisibility.value = value
    }

    fun saveList() = viewModelScope.launch {
        if (!isEdited.value) return@launch
        val list = list.value ?: List()
        listRepository.updateList(
            list = list.copy(
                title = title.value,
                updated = Clock.System.now(),
                items = items.value,
                isPinned = pinned.value,
                backgroundColor = backgroundColor.value
            )
        )
        _isEdited.value = false
    }

    fun copyList() = viewModelScope.launch {
        val list = list.value?.copy(
            id = 0,
            created = Clock.System.now(),
            updated = Clock.System.now()
        ) ?: return@launch
        listRepository.updateList(list = list)
    }

    fun deleteList() = viewModelScope.launch {
        val list = list.value ?: return@launch
        _isDeleted.value = listRepository.deleteList(list = list).isSuccess
    }

    fun completeItem(item: Item) = viewModelScope.launch {
        val index = items.value.indexOfFirst { it === item }
        _isEdited.value = true
        _items.update {
            val modified = it.toMutableList()
            modified[index] = modified[index].copy(isCompleted = !modified[index].isCompleted)

            modified
        }
    }

    fun deleteItem(item: Item) = viewModelScope.launch {

        if (item.id == 0L) {
            _items.update {
                val modified = it.toMutableList()
                modified.remove(element = item)

                modified
            }
            return@launch
        }
        listRepository.deleteItem(item = item)
    }
}
