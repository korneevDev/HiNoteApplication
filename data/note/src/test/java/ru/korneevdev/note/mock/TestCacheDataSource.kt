package ru.korneevdev.note.mock

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevdev.note.NoteCacheDataSource
import ru.korneevdev.entity.entity.Note
import ru.korneevdev.entity.entity.NoteTimeStamp
import ru.korneevdev.entity.entity.SimpleNote

class TestCacheDataSource : NoteCacheDataSource {

    val notes = mutableListOf<ru.korneevdev.entity.entity.Note>()
    override suspend fun getNote(id: Int): Flow<ru.korneevdev.entity.entity.Note> = flow {
        emit(notes[id])
    }

    override suspend fun saveNote(note: ru.korneevdev.entity.entity.SimpleNote, timeStamp: ru.korneevdev.entity.entity.NoteTimeStamp): Flow<Int> =
        saveNote(ru.korneevdev.entity.entity.Note(notes.size, timeStamp, note))

    override suspend fun saveNote(note: ru.korneevdev.entity.entity.Note): Flow<Int> {
        notes.add(note)
        return flow{
            emit(notes.lastIndex)
        }
    }

    override suspend fun deleteNote(id: Int): ru.korneevdev.entity.entity.Note = notes[id].also {
        notes.removeAt(id)
    }
}