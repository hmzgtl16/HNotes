package com.example.hnotes.core.data.repository

import com.example.hnotes.core.data.util.toEntity
import com.example.hnotes.core.data.util.toModel
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.dao.NoteFtsDao
import com.example.hnotes.core.database.dao.SearchQueryDao
import com.example.hnotes.core.database.model.PopulatedNoteEntity
import com.example.hnotes.core.database.model.SearchQueryEntity
import com.example.hnotes.core.model.SearchQuery
import com.example.hnotes.core.model.SearchResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao,
    private val searchQueryDao: SearchQueryDao,
    private val noteFtsDao: NoteFtsDao
) : SearchRepository {
    override suspend fun getSearchContents(searchQuery: SearchQuery): Flow<SearchResult> {
        val noteIds = noteFtsDao.searchAllNotes(text = "*${searchQuery.query}*")
        val noteIdsWithItems = noteFtsDao.searchAllItems(text = "*${searchQuery.query}*")

        val noteFlow = combine(
            flow = noteIds,
            flow2 = noteIdsWithItems
        ) { ids, idsWithItems -> (ids + idsWithItems).toSet() }
            .distinctUntilChanged()
            .flatMapLatest(noteDao::getNotesByIds)

        return noteFlow
            .map { it.map(PopulatedNoteEntity::toModel) }
            .map(::SearchResult)
    }

    override fun getAllSearchQueries(limit: Int): Flow<List<SearchQuery>> =
        searchQueryDao.getSearchQueries(limit = limit)
            .map { it.map(SearchQueryEntity::toModel) }

    override suspend fun insertOrReplaceSearchQuery(searchQuery: SearchQuery) =
        searchQueryDao.upsertSearchQuery(searchQuery = searchQuery.toEntity())

    override suspend fun delete(searchQuery: SearchQuery) =
        searchQueryDao.delete(searchQuery = searchQuery.toEntity())

    override suspend fun deleteAll() =
        searchQueryDao.deleteAll()
}
