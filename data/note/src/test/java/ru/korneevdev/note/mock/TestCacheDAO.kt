package ru.korneevdev.note.mock

import kotlinx.coroutines.flow.flow
import ru.korneevdev.note.room.NoteCacheModel
import ru.korneevdev.note.room.NoteDAO

class TestCacheDAO : NoteDAO {

    val notes = mutableListOf<NoteCacheModel>()
    override fun getNote(id: Int) = flow { emit(notes[id]) }

    override fun saveNote(note: NoteCacheModel): Long {
        notes.add(note)
        val id = notes.lastIndex
        notes[id].id = id
        return id.toLong()
    }

    override fun deleteNote(note: NoteCacheModel) {
        notes.remove(note)
    }
}