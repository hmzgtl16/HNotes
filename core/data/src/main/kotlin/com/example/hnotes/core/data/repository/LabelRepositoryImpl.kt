package com.example.hnotes.core.data.repository

import android.util.Log
import com.example.hnotes.core.data.util.toEntity
import com.example.hnotes.core.data.util.toModel
import com.example.hnotes.core.data.util.toEntity
import com.example.hnotes.core.data.util.toModel
import com.example.hnotes.core.database.dao.LabelDao
import com.example.hnotes.core.database.model.LabelEntity
import com.example.hnotes.core.database.model.NoteLabelCrossRef
import com.example.hnotes.core.model.Label
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LabelRepositoryImpl @Inject constructor(
    private val labelDao: LabelDao
) : LabelRepository {
    override suspend fun upsertLabel(label: Label): Long {
        return labelDao.upsertLabel(label.toEntity())
    }

    override suspend fun deleteLabel(label: Label) {
        labelDao.deleteLabel(label.toEntity())
    }

    override fun getAllLabels(): Flow<List<Label>> {
        return labelDao.getAllLabels().map { it.map(LabelEntity::toModel) }
    }

    override suspend fun getLabelById(id: Long): Label? {
        return labelDao.getLabelById(id)?.toModel()
    }

    override suspend fun getLabelByName(name: String): Label? {
        return labelDao.getLabelByName(name)?.toModel()
    }

    override fun getLabelsForNote(noteId: Long): Flow<List<Label>> {
        return labelDao.getLabelsForNote(noteId).map { it.map(LabelEntity::toModel) }
    }

    override suspend fun linkLabelToNote(noteId: Long, labelId: Long) {
        labelDao.insertNoteLabelCrossRef(NoteLabelCrossRef(noteId, labelId))
    }

    override suspend fun unlinkLabelFromNote(noteId: Long, labelId: Long) {
        labelDao.deleteNoteLabelCrossRef(NoteLabelCrossRef(noteId, labelId))
    }
}



