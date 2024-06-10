package ru.korneevdev.note.mock

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevdev.note.CacheDataSource
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteTimeStamp
import ru.korneevdev.note.entity.SimpleNote

class TestCacheDataSource : CacheDataSource {

    val notes = mutableListOf<Note>()
    override suspend fun getNote(id: Int): Flow<Note> = flow {
        emit(notes[id])
    }

    override suspend fun saveNote(note: SimpleNote, timeStamp: NoteTimeStamp): Flow<Int> =
        saveNote(Note(notes.size, timeStamp, note))

    override suspend fun saveNote(note: Note): Flow<Int> {
        notes.add(note)
        return flow{
            emit(notes.lastIndex)
        }
    }

    override suspend fun deleteNote(id: Int): Note = notes[id].also {
        notes.removeAt(id)
    }
}