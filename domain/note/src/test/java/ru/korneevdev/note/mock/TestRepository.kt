package ru.korneevdev.note.mock

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevdev.entity.entity.Note
import ru.korneevdev.entity.entity.NoteTimeStamp
import ru.korneevdev.entity.entity.ProcessingState
import ru.korneevdev.entity.entity.SimpleNote
import ru.korneevdev.entity.test_utils.TestTimeStampManager
import ru.korneevdev.note.DeleteNoteRepository
import ru.korneevdev.note.GetNoteRepository
import ru.korneevdev.note.SaveNoteRepository
import ru.korneevdev.note.exception.OutOfMemoryException

class TestRepository(
    private val timeStampManager: TestTimeStampManager = TestTimeStampManager()
) : GetNoteRepository, SaveNoteRepository, DeleteNoteRepository {

    val notesList = mutableListOf<SimpleNote>()
    private var timeStamp: Long? = null
    private var lastNoteId = 0
    private val maxSize = TestConstants.maxSavedNotes

    var deletedNote: SimpleNote? = null

    override suspend fun getNote(id: Int): Flow<Note> =
        flow {
            emit(
                Note(
                    id,
                    if (timeStamp != null)
                        NoteTimeStamp.Updated(timeStamp!!, id.toLong())
                            .also { timeStamp = null }
                    else
                        NoteTimeStamp.FirstCreated(id.toLong()),
                    notesList[id]
                )
            )
        }

    override suspend fun saveNote(
        note: SimpleNote,
        currentTimeStamp: NoteTimeStamp
    ): Flow<ProcessingState> =
        if (notesList.size < maxSize) {
            notesList.add(note)
            flow { emit(ProcessingState.Created(lastNoteId++)) }
        } else throw OutOfMemoryException(TestStringResourceProvider())

    override suspend fun saveNote(
        newNote: SimpleNote,
        currentTime: Long,
        oldNoteId: Int
    ): Flow<ProcessingState> =
        saveNote(newNote, timeStampManager.getCurrentTimeStamp()).also {
            this.timeStamp = oldNoteId.toLong()
        }

    override suspend fun deleteNote(id: Int) {
        deletedNote = notesList[id]
        notesList.removeAt(id)
    }

    override suspend fun restoreDeletedNote() {
        notesList.add(deletedNote!!)
        deletedNote = null
    }
}