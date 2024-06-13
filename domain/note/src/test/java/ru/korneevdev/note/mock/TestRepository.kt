package ru.korneevdev.note.mock

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevdev.note.DeleteNoteRepository
import ru.korneevdev.note.GetNoteRepository
import ru.korneevdev.note.SaveNoteRepository
import ru.korneevdev.entity.entity.Note
import ru.korneevdev.entity.entity.NoteTimeStamp
import ru.korneevdev.entity.entity.ProcessingState
import ru.korneevdev.entity.entity.SimpleNote
import ru.korneevdev.note.exception.OutOfMemoryException
import ru.korneevdev.entity.test_utils.TestTimeStampManager

class TestRepository(
    private val timeStampManager: TestTimeStampManager = TestTimeStampManager()
) : GetNoteRepository, SaveNoteRepository, DeleteNoteRepository {

    val notesList = mutableListOf<ru.korneevdev.entity.entity.SimpleNote>()
    private var timeStamp: Long? = null
    private var lastNoteId = 0
    private val maxSize = TestConstants.maxSavedNotes

    var deletedNote: ru.korneevdev.entity.entity.SimpleNote? = null

    override suspend fun getNote(id: Int): Flow<ru.korneevdev.entity.entity.Note> =
        flow {
            emit(
                ru.korneevdev.entity.entity.Note(
                    id,
                    if (timeStamp != null)
                        ru.korneevdev.entity.entity.NoteTimeStamp.Updated(timeStamp!!, id.toLong())
                            .also { timeStamp = null }
                    else
                        ru.korneevdev.entity.entity.NoteTimeStamp.FirstCreated(id.toLong()),
                    notesList[id]
                )
            )
        }

    override suspend fun saveNote(
        note: ru.korneevdev.entity.entity.SimpleNote,
        currentTimeStamp: ru.korneevdev.entity.entity.NoteTimeStamp
    ): Flow<ru.korneevdev.entity.entity.ProcessingState> =
        if (notesList.size < maxSize) {
            notesList.add(note)
            flow { emit(ru.korneevdev.entity.entity.ProcessingState.Created(lastNoteId++)) }
        } else throw OutOfMemoryException(TestStringResourceProvider())

    override suspend fun saveNote(
        newNote: ru.korneevdev.entity.entity.SimpleNote,
        currentTime: Long,
        oldNoteId: Int
    ): Flow<ru.korneevdev.entity.entity.ProcessingState> =
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