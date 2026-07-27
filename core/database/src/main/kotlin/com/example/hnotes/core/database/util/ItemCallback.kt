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
package com.example.hnotes.core.database.util

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

val ItemCallback =
    object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            db.apply {
                execSQL(sql = ITEM_INSERT_TRIGGER)
                execSQL(sql = ITEM_UPDATE_TRIGGER)
                execSQL(sql = ITEM_DELETE_TRIGGER)
            }
        }
    }

private const val ITEM_INSERT_TRIGGER =
    """
        CREATE TRIGGER IF NOT EXISTS insert_item_fts AFTER INSERT ON items
        BEGIN
            INSERT INTO items_fts(item_fts_id, item_fts_content, item_fts_note_id)
            VALUES (new.id, new.content, new.noteId);
        END;
    """

private const val ITEM_UPDATE_TRIGGER =
    """
        CREATE TRIGGER IF NOT EXISTS update_item_fts AFTER UPDATE ON items
        BEGIN
            UPDATE items_fts
            SET item_fts_content = new.content
            WHERE item_fts_id = new.id;
        END;
    """

private const val ITEM_DELETE_TRIGGER =
    """
        CREATE TRIGGER IF NOT EXISTS delete_item_fts AFTER DELETE ON items
        BEGIN
            DELETE FROM items_fts
            WHERE item_fts_id = old.id;
        END;
    """
