package ru.korneevdev.note.mock

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevdev.note.GetNoteRepository
import ru.korneevdev.note.SaveNoteRepository
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteId
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.exception.OutOfMemoryException

class TestRepository : GetNoteRepository, SaveNoteRepository {

    val notesList = mutableListOf<Note>()
    var lastNoteId = 0
    var maxSize = TestConstants.maxSavedNotes
    override suspend fun getNote(id: NoteId): Flow<Note> =
        flow {
            emit(notesList.first {
                it.checkIdSame(id)
            })
        }

    override suspend fun saveNote(note: Note): Flow<ProcessingState> =
        if (notesList.size < maxSize) {
            notesList.add(note)
            flow { emit(ProcessingState.Success(NoteId.Id(lastNoteId++))) }
        } else throw OutOfMemoryException(TestStringResourceProvider())
}