package com.example.hnotes.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.hnotes.core.database.model.LabelEntity
import com.example.hnotes.core.database.model.NoteLabelCrossRef
import kotlinx.coroutines.flow.Flow

@Dao
interface LabelDao {
    @Upsert
    suspend fun upsertLabel(label: LabelEntity): Long

    @Delete
    suspend fun deleteLabel(label: LabelEntity)

    @Query("SELECT * FROM labels ORDER BY name ASC")
    fun getAllLabels(): Flow<List<LabelEntity>>

    @Query("SELECT * FROM labels WHERE id = :id")
    suspend fun getLabelById(id: Long): LabelEntity?

    @Query("SELECT * FROM labels WHERE name = :name")
    suspend fun getLabelByName(name: String): LabelEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNoteLabelCrossRef(crossRef: NoteLabelCrossRef)

    @Delete
    suspend fun deleteNoteLabelCrossRef(crossRef: NoteLabelCrossRef)

    @Transaction
    @Query(
        """
        SELECT labels.* FROM labels
        INNER JOIN note_label_cross_ref ON labels.id = note_label_cross_ref.labelId
        WHERE note_label_cross_ref.noteId = :noteId
        """
    )
    fun getLabelsForNote(noteId: Long): Flow<List<LabelEntity>>

    @Query("DELETE FROM note_label_cross_ref WHERE noteId = :noteId")
    suspend fun deleteLabelsByNoteId(noteId: Long)

    @Query("DELETE FROM note_label_cross_ref WHERE labelId = :labelId")
    suspend fun deleteNotesByLabelId(labelId: Long)
}
