package com.example.hnotes.core.data.repository

import com.example.hnotes.core.model.Label
import kotlinx.coroutines.flow.Flow

interface LabelRepository {
    suspend fun upsertLabel(label: Label): Long
    suspend fun deleteLabel(label: Label)
    fun getAllLabels(): Flow<List<Label>>
    suspend fun getLabelById(id: Long): Label?
    suspend fun getLabelByName(name: String): Label?
    fun getLabelsForNote(noteId: Long): Flow<List<Label>>
    suspend fun linkLabelToNote(noteId: Long, labelId: Long)
    suspend fun unlinkLabelFromNote(noteId: Long, labelId: Long)
}
