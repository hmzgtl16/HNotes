package com.example.hnotes.core.database.util

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

val ItemCallback = object : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        db.apply {
            execSQL(sql = itemInsertTrigger)
            db.execSQL(sql = itemUpdateTrigger)
            db.execSQL(sql = itemDeleteTrigger)
        }
    }
}

private const val itemInsertTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS insert_item_fts AFTER INSERT ON table_item
        BEGIN 
            INSERT INTO table_item_fts(item_fts_id, item_fts_title)
            VALUES (new.item_id, new.item_title);
        END;
    """

private const val itemUpdateTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS update_item_fts AFTER UPDATE ON table_item
        BEGIN 
            UPDATE table_item_fts
            SET item_fts_title = new.item_title
            WHERE item_fts_id = new.item_id;
        END;
    """

private const val itemDeleteTrigger =
    """
        CREATE TRIGGER IF NOT EXISTS delete_item_fts AFTER DELETE ON table_item
        BEGIN 
            DELETE FROM table_item_fts
            WHERE item_fts_id = old.item_id;
        END;
    """
