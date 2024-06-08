package ru.korneevdev.note.mock

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevdev.note.GetNoteRepository
import ru.korneevdev.note.SaveNoteRepository
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteTimeStamp
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.entity.SimpleNote
import ru.korneevdev.note.exception.OutOfMemoryException

class TestRepository : GetNoteRepository, SaveNoteRepository {

    val notesList = mutableListOf<SimpleNote>()
    var timeStamp = NoteTimeStamp(10L, 20L)
    var lastNoteId = 0
    private val maxSize = TestConstants.maxSavedNotes
    override suspend fun getNote(id: Int): Flow<Note> =
        flow {
            emit(Note(
                id,
                timeStamp,
                notesList[id]
            ))
        }

    override suspend fun saveNote(note: SimpleNote): Flow<ProcessingState> =
        if (notesList.size < maxSize) {
            notesList.add(note)
            flow { emit(ProcessingState.Success(lastNoteId++)) }
        } else throw OutOfMemoryException(TestStringResourceProvider())

    override suspend fun saveNote(newNote: SimpleNote, oldNoteId: Int): Flow<ProcessingState> =
        saveNote(newNote)
}