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
package com.example.hnotes.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.hnotes.core.database.dao.LabelDao
import com.example.hnotes.core.database.dao.NoteDao
import com.example.hnotes.core.database.dao.NoteFtsDao
import com.example.hnotes.core.database.dao.SearchQueryDao
import com.example.hnotes.core.database.model.ItemEntity
import com.example.hnotes.core.database.model.ItemFtsEntity
import com.example.hnotes.core.database.model.LabelEntity
import com.example.hnotes.core.database.model.NoteEntity
import com.example.hnotes.core.database.model.NoteFtsEntity
import com.example.hnotes.core.database.model.NoteLabelCrossRef
import com.example.hnotes.core.database.model.ReminderEntity
import com.example.hnotes.core.database.model.SearchQueryEntity
import com.example.hnotes.core.database.util.InstantConverter
import com.example.hnotes.core.database.util.RepeatModeConverter

@Database(
    entities = [
        NoteEntity::class,
        ReminderEntity::class,
        ItemEntity::class,
        SearchQueryEntity::class,
        NoteFtsEntity::class,
        ItemFtsEntity::class,
        LabelEntity::class,
        NoteLabelCrossRef::class,
    ],
    version = 3,
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
    RepeatModeConverter::class,
)
internal abstract class ApplicationDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

    abstract fun labelDao(): LabelDao

    abstract fun searchQueryDao(): SearchQueryDao

    abstract fun noteFtsDao(): NoteFtsDao
}
