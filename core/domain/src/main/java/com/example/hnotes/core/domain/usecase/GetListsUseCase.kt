package com.example.hnotes.core.domain.usecase

import com.example.hnotes.core.data.repository.ListRepository
import com.example.hnotes.core.model.data.List
import com.example.hnotes.core.model.data.Note
import kotlin.collections.List as KList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetListsUseCase @Inject constructor(
    private val listRepository: ListRepository
) {

    operator fun invoke(): Flow<Map<Boolean, KList<List>>> =
        listRepository.lists
            .map {
                it
                    .sortedByDescending(selector = List::created)
                    .groupBy(keySelector = List::isPinned)
                    .toSortedMap(comparator = compareBy(Boolean::not))
            }
}