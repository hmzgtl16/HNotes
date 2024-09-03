package com.example.hnotes.core.model.data

data class SearchResult(
    val notes: kotlin.collections.List<Note> = emptyList(),
    val tasks: kotlin.collections.List<Task> = emptyList(),
    val lists: kotlin.collections.List<List> = emptyList()
) {
    fun isEmpty(): Boolean = notes.isEmpty() && tasks.isEmpty() && lists.isEmpty()
}
