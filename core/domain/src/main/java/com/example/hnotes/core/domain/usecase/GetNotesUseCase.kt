package com.example.hnotes.core.domain.usecase

import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.model.data.Note
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository,
) {

    operator fun invoke(): Flow<Map<Boolean, List<Note>>> =
        noteRepository.notes
            .map {
                it
                    .sortedByDescending(selector = Note::created)
                    .groupBy(keySelector = Note::isPinned)
                    .toSortedMap(comparator = compareBy(Boolean::not))
            }
}


