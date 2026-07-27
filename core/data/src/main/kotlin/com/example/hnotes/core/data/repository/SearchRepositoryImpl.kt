/*
 * Copyright (c) 2026 GATTAL Hamza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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

class SearchRepositoryImpl
    @Inject
    constructor(private val noteDao: NoteDao, private val searchQueryDao: SearchQueryDao, private val noteFtsDao: NoteFtsDao) : SearchRepository {
        override suspend fun getSearchContents(searchQuery: SearchQuery): Flow<SearchResult> {
            val noteIds = noteFtsDao.searchAllNotes(text = "*${searchQuery.query}*")
            val noteIdsWithItems = noteFtsDao.searchAllItems(text = "*${searchQuery.query}*")

            val noteFlow =
                combine(
                    flow = noteIds,
                    flow2 = noteIdsWithItems,
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

        override suspend fun insertOrReplaceSearchQuery(searchQuery: SearchQuery) = searchQueryDao.upsertSearchQuery(searchQuery = searchQuery.toEntity())

        override suspend fun delete(searchQuery: SearchQuery) = searchQueryDao.delete(searchQuery = searchQuery.toEntity())

        override suspend fun deleteAll() = searchQueryDao.deleteAll()
    }
