package com.example.hnotes.core.database.util

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

val NoteCallback = object : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.apply {
            execSQL(sql = noteInsertTrigger)
            db.execSQL(sql = noteUpdateTrigger)
            db.execSQL(sql = noteDeleteTrigger)
        }
    }
}

private const val noteInsertTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS insert_note_fts AFTER INSERT ON table_note
        BEGIN 
            INSERT INTO table_note_fts(note_fts_id, note_fts_title, note_fts_description)
            VALUES (new.note_id, new.note_title, new.note_description);
        END;
    """

private const val noteUpdateTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS update_note_fts AFTER UPDATE ON table_note
        BEGIN 
            UPDATE table_note_fts
            SET note_fts_title = new.note_title, note_fts_description = new.note_description
            WHERE note_fts_id = new.note_id;
        END;
    """

private const val noteDeleteTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS delete_note_fts AFTER DELETE ON table_note
        BEGIN 
            DELETE FROM table_note_fts
            WHERE note_fts_id = old.note_id;
        END;
    """
