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
import com.example.hnotes.core.database.dao.LabelDao
import com.example.hnotes.core.database.model.LabelEntity
import com.example.hnotes.core.database.model.NoteLabelCrossRef
import com.example.hnotes.core.model.Label
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LabelRepositoryImpl
@Inject
constructor(private val labelDao: LabelDao) : LabelRepository {
    override suspend fun upsertLabel(label: Label): Long = labelDao.upsertLabel(label.toEntity())

    override suspend fun deleteLabel(label: Label) {
        labelDao.deleteLabel(label.toEntity())
    }

    override fun getAllLabels(): Flow<List<Label>> = labelDao.getAllLabels().map { it.map(LabelEntity::toModel) }

    override suspend fun getLabelById(id: Long): Label? = labelDao.getLabelById(id)?.toModel()

    override suspend fun getLabelByName(name: String): Label? = labelDao.getLabelByName(name)?.toModel()

    override fun getLabelsForNote(noteId: Long): Flow<List<Label>> = labelDao.getLabelsForNote(noteId).map { it.map(LabelEntity::toModel) }

    override suspend fun linkLabelToNote(noteId: Long, labelId: Long) {
        labelDao.insertNoteLabelCrossRef(NoteLabelCrossRef(noteId, labelId))
    }

    override suspend fun unlinkLabelFromNote(noteId: Long, labelId: Long) {
        labelDao.deleteNoteLabelCrossRef(NoteLabelCrossRef(noteId, labelId))
    }
}
