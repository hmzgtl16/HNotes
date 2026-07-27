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

val NoteCallback =
    object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            db.apply {
                execSQL(sql = NOTE_INSERT_TRIGGER)
                execSQL(sql = NOTE_UPDATE_TRIGGER)
                execSQL(sql = NOTE_DELETE_TRIGGER)
            }
        }
    }

private const val NOTE_INSERT_TRIGGER =
    """
        CREATE TRIGGER IF NOT EXISTS insert_note_fts AFTER INSERT ON notes
        BEGIN
            INSERT INTO notes_fts(note_fts_id, note_fts_title, note_fts_content)
            VALUES (new.id, new.title, new.content);
        END;
    """

private const val NOTE_UPDATE_TRIGGER =
    """
        CREATE TRIGGER IF NOT EXISTS update_note_fts AFTER UPDATE ON notes
        BEGIN
            UPDATE notes_fts
            SET note_fts_title = new.title, note_fts_content = new.content
            WHERE note_fts_id = new.id;
        END;
    """

private const val NOTE_DELETE_TRIGGER =
    """
        CREATE TRIGGER IF NOT EXISTS delete_note_fts AFTER DELETE ON notes
        BEGIN
            DELETE FROM notes_fts
            WHERE note_fts_id = old.id;
        END;
    """
