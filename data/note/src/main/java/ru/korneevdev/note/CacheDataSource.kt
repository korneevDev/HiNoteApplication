package ru.korneevdev.note

import kotlinx.coroutines.flow.Flow
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteTimeStamp
import ru.korneevdev.note.entity.SimpleNote

interface CacheDataSource {

    suspend fun getNote(id: Int): Flow<Note>

    suspend fun saveNote(note: SimpleNote, timeStamp: NoteTimeStamp): Flow<Int>

    suspend fun saveNote(note: Note): Flow<Int>

    suspend fun deleteNote(id: Int): Note
}