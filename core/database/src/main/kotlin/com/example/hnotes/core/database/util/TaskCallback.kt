package com.example.hnotes.core.database.util

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

val TaskCallback = object : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.apply {
            execSQL(sql = taskInsertTrigger)
            execSQL(sql = taskUpdateTrigger)
            execSQL(sql = taskDeleteTrigger)
        }
    }
}

private const val taskInsertTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS insert_task_fts AFTER INSERT ON table_task
        BEGIN 
            INSERT INTO table_task_fts(task_fts_id, task_fts_title)
            VALUES (new.task_id, new.task_title);
        END;
    """

private const val taskUpdateTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS update_task_fts AFTER UPDATE ON table_task
        BEGIN 
            UPDATE table_task_fts
            SET task_fts_title = new.task_title
            WHERE task_fts_id = new.task_id;
        END;
    """

private const val taskDeleteTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS delete_task_fts AFTER DELETE ON table_task
        BEGIN
            DELETE FROM table_task_fts
            WHERE task_fts_id = old.task_id;
        END;
    """