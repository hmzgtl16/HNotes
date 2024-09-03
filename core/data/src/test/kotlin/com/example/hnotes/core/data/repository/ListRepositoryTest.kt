package com.example.hnotes.core.data.repository

import com.example.hnotes.core.data.dao.TestListDao
import org.junit.Before

class ListRepositoryTest {

    private lateinit var repository: ListRepositoryImpl
    private lateinit var dao: TestListDao

    @Before
    fun setup() {
        dao = TestListDao()
        repository = ListRepositoryImpl(listDao = dao)
    }
}