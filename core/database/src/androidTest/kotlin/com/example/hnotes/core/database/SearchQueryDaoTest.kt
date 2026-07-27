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
package com.example.hnotes.core.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.hnotes.core.database.dao.SearchQueryDao
import com.example.hnotes.core.database.model.SearchQueryEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertTrue
import kotlin.time.Instant

class SearchQueryDaoTest {
    private lateinit var db: ApplicationDatabase
    private lateinit var searchQueryDao: SearchQueryDao

    @BeforeTest
    fun setup() {
        db =
            run {
                val context = ApplicationProvider.getApplicationContext<Context>()
                Room.inMemoryDatabaseBuilder(
                    context,
                    ApplicationDatabase::class.java,
                )
                    .build()
            }
        searchQueryDao = db.searchQueryDao()
    }

    @AfterTest
    fun teardown() = db.close()

    @Test
    fun upsertSearchQueryInsertNewEntity() =
        runTest {
            val searchQuery =
                testSearchQueryEntity(
                    text = "test query",
                    queried = Instant.parse("2023-10-01T10:00:00Z"),
                )
            searchQueryDao.upsertSearchQuery(searchQuery)

            val searchQueries = searchQueryDao.getSearchQueries(limit = 10).first()

            assertContains(iterable = searchQueries, element = searchQuery)
        }

    @Test
    fun upsertSearchQueryUpdateExistingEntity() =
        runTest {
            val searchQuery =
                testSearchQueryEntity(
                    text = "test query to update",
                    queried = Instant.parse("2023-10-01T10:00:00Z"),
                )
            searchQueryDao.upsertSearchQuery(searchQuery)

            val updatedSearchQuery =
                searchQuery.copy(
                    queried = Instant.parse("2023-10-02T10:00:00Z"),
                )
            searchQueryDao.upsertSearchQuery(updatedSearchQuery)

            val searchQueries = searchQueryDao.getSearchQueries(limit = 10).first()

            assertContains(iterable = searchQueries, element = updatedSearchQuery)
            assertTrue(actual = !searchQueries.contains(searchQuery))
        }

    @Test
    fun deleteAllWithNonEmptyTable() =
        runTest {
            val searchQuery1 =
                testSearchQueryEntity(
                    text = "test query 1",
                    queried = Instant.parse("2023-10-01T10:00:00Z"),
                )
            val searchQuery2 =
                testSearchQueryEntity(
                    text = "test query 2",
                    queried = Instant.parse("2023-10-02T10:00:00Z"),
                )
            searchQueryDao.upsertSearchQuery(searchQuery1)
            searchQueryDao.upsertSearchQuery(searchQuery2)

            searchQueryDao.deleteAll()

            val searchQueries = searchQueryDao.getSearchQueries(limit = 10).first()

            assertTrue(actual = searchQueries.isEmpty())
        }

    @Test
    fun deleteAllWithETable() =
        runTest {
            searchQueryDao.deleteAll()

            val searchQueries = searchQueryDao.getSearchQueries(limit = 10).first()

            assertTrue(actual = searchQueries.isEmpty())
        }

    @Test
    fun deleteExistingEntity() =
        runTest {
            val searchQuery1 =
                testSearchQueryEntity(
                    text = "test query 1",
                    queried = Instant.parse("2023-10-01T10:00:00Z"),
                )
            val searchQuery2 =
                testSearchQueryEntity(
                    text = "test query 2",
                    queried = Instant.parse("2023-10-02T10:00:00Z"),
                )
            searchQueryDao.upsertSearchQuery(searchQuery1)
            searchQueryDao.upsertSearchQuery(searchQuery2)

            searchQueryDao.delete(searchQuery1)

            val searchQueries = searchQueryDao.getSearchQueries(limit = 10).first()

            assertContains(iterable = searchQueries, element = searchQuery2)
            assertTrue(actual = !searchQueries.contains(searchQuery1))
        }

    private fun testSearchQueryEntity(text: String = "Test Query", queried: Instant = Instant.parse("2023-10-01T10:00:00Z")) =
        SearchQueryEntity(
            text = text,
            queried = queried,
        )
}
