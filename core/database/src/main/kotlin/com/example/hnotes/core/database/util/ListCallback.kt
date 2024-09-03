package com.example.hnotes.core.database.util

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

val ListCallback = object : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.apply {
            execSQL(sql = listInsertTrigger)
            db.execSQL(sql = listUpdateTrigger)
            db.execSQL(sql = listDeleteTrigger)
        }
    }
}

private const val listInsertTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS insert_list_fts AFTER INSERT ON table_list
        BEGIN 
            INSERT INTO table_list_fts(list_fts_id, list_fts_title)
            VALUES (new.list_id, new.list_title);
        END;
    """

private const val listUpdateTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS update_list_fts AFTER UPDATE ON table_list
        BEGIN 
            UPDATE table_list_fts
            SET list_fts_title = new.list_title
            WHERE list_fts_id = new.list_id;
        END;
    """

private const val listDeleteTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS delete_list_fts AFTER DELETE ON table_list
        BEGIN 
            DELETE FROM table_list_fts
            WHERE list_fts_id = old.list_id;
        END;
    """
