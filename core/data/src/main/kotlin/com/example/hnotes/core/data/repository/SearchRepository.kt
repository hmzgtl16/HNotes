package com.example.hnotes.core.data.repository

import com.example.hnotes.core.data.utils.asEntity
import com.example.hnotes.core.database.dao.ListDao
import com.example.hnotes.core.database.dao.ListFtsDao
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.dao.NoteFtsDao
import com.example.hnotes.core.database.dao.SearchQueryDao
import com.example.hnotes.core.database.dao.TaskDao
import com.example.hnotes.core.database.dao.TaskFtsDao
import com.example.hnotes.core.database.model.ListWithItems
import com.example.hnotes.core.database.model.NoteEntity
import com.example.hnotes.core.database.model.SearchQueryEntity
import com.example.hnotes.core.database.model.TaskEntity
import com.example.hnotes.core.database.model.asExternalModel
import com.example.hnotes.core.model.data.SearchQuery
import com.example.hnotes.core.model.data.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

interface SearchRepository {

    suspend fun getSearchContents(searchQuery: SearchQuery): Flow<SearchResult>

     fun getSearchQueries(limit: Int): Flow<List<SearchQuery>>

    suspend fun insertOrReplaceSearchQuery(searchQuery: SearchQuery)

    suspend fun clearSearchQuery(searchQuery: SearchQuery)

    suspend fun clearAllSearchQueries()
}

class SearchRepositoryImpl @Inject constructor(
    private val listDao: ListDao,
    private val listFtsDao: ListFtsDao,
    private val noteDao: NoteDao,
    private val noteFtsDao: NoteFtsDao,
    private val taskDao: TaskDao,
    private val taskFtsDao: TaskFtsDao,
    private val searchQueryDao: SearchQueryDao,
) : SearchRepository {

    override suspend fun getSearchContents(searchQuery: SearchQuery): Flow<SearchResult> {

        val noteIds = noteFtsDao.searchAllNotes(text = "*${searchQuery.query}*")
        val taskIds = taskFtsDao.searchAllTasks(text = "*${searchQuery.query}*")
        val listIds = combine(
            flow = listFtsDao.searchAllLists(text = "*${searchQuery.query}*"),
            flow2 = listFtsDao.searchAllLists(text = "*${searchQuery.query}*"),
            transform = { list1, list2 -> list1 + list2 }
        )

        val noteFlow = noteIds
            .mapLatest(List<String>::toSet)
            .distinctUntilChanged()
            .flatMapLatest(noteDao::getNotesByIds)

        val taskFlow = taskIds
            .mapLatest(List<String>::toSet)
            .distinctUntilChanged()
            .flatMapLatest(taskDao::getTasksByIds)

        val listFlow = listIds
            .mapLatest(List<String>::toSet)
            .distinctUntilChanged()
            .flatMapLatest(listDao::getListsByIds)

        return combine(
            flow = noteFlow,
            flow2 = taskFlow,
            flow3 = listFlow,
            transform = { notes, tasks, lists ->
                SearchResult(
                    notes = notes.map(NoteEntity::asExternalModel),
                    tasks = tasks.map(TaskEntity::asExternalModel),
                    lists = lists.map(ListWithItems::asExternalModel),
                )
            }
        )
    }

    override fun getSearchQueries(limit: Int): Flow<List<SearchQuery>> =
        searchQueryDao.getSearchQueries(limit = limit).map { searchQueries ->
            searchQueries.map(SearchQueryEntity::asExternalModel)
        }

    override suspend fun insertOrReplaceSearchQuery(searchQuery: SearchQuery) =
        searchQueryDao.upsertSearchQuery(searchQuery = searchQuery.asEntity())

    override suspend fun clearSearchQuery(searchQuery: SearchQuery) =
        searchQueryDao.delete(searchQueryEntity = searchQuery.asEntity())

    override suspend fun clearAllSearchQueries() = searchQueryDao.deleteAll()
}