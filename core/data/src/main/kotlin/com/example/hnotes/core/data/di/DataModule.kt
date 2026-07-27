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
package com.example.hnotes.core.data.di

import com.example.hnotes.core.data.repository.LabelRepository
import com.example.hnotes.core.data.repository.LabelRepositoryImpl
import com.example.hnotes.core.data.repository.NoteRepository
import com.example.hnotes.core.data.repository.NoteRepositoryImpl
import com.example.hnotes.core.data.repository.SearchRepository
import com.example.hnotes.core.data.repository.SearchRepositoryImpl
import com.example.hnotes.core.data.repository.UserDataRepository
import com.example.hnotes.core.data.repository.UserDataRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindsUserDataRepository(userDataRepository: UserDataRepositoryImpl): UserDataRepository

    @Binds
    internal abstract fun bindsNoteRepository(noteRepository: NoteRepositoryImpl): NoteRepository

    @Binds
    internal abstract fun bindsLabelRepository(labelRepository: LabelRepositoryImpl): LabelRepository

    @Binds
    internal abstract fun bindsSearchRepository(searchRepository: SearchRepositoryImpl): SearchRepository
}
