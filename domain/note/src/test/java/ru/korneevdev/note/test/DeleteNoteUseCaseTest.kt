package ru.korneevdev.note.test

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.korneevdev.note.entity.NoteColor
import ru.korneevdev.note.entity.NoteContent
import ru.korneevdev.note.entity.NoteHeader
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.entity.SimpleNote
import ru.korneevdev.note.mock.TestExceptionHandler
import ru.korneevdev.note.mock.TestRepository
import ru.korneevdev.note.use_case.DeleteNoteUseCase
import ru.korneevdev.note.use_case.SaveNoteUseCase

class DeleteNoteUseCaseTest {

    private val note1 = SimpleNote(
        NoteHeader("Test note header 0"),
        NoteContent("Test note content 1"),
        NoteColor(0)
    )

    @Test
    fun saveAndDeleteNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler
            )
        val deleteNoteUseCase = DeleteNoteUseCase.Base(
            repository
        )

        var expected: ProcessingState = ProcessingState.Created(0)
        var actualFlow: Flow<ProcessingState> = saveNoteUseCase.saveNote(note1)
        assertEquals(expected, actualFlow.first())

        var expectedSavedNotesCount = 1
        var actualSavedNotesCount = repository.notesList.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        expected = ProcessingState.Deleted(0)
        actualFlow = deleteNoteUseCase.deleteNote(0)
        assertEquals(expected, actualFlow.first())

        expectedSavedNotesCount = 0
        actualSavedNotesCount = repository.notesList.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)
    }

    @Test
    fun deleteAndRestoreNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler
            )
        val deleteNoteUseCase = DeleteNoteUseCase.Base(
            repository
        )

        var expected: ProcessingState = ProcessingState.Created(0)
        var actualFlow: Flow<ProcessingState> = saveNoteUseCase.saveNote(note1)
        assertEquals(expected, actualFlow.first())

        var expectedSavedNotesCount = 1
        var actualSavedNotesCount = repository.notesList.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        expected = ProcessingState.Deleted(0)
        actualFlow = deleteNoteUseCase.deleteNote(0)
        assertEquals(expected, actualFlow.first())

        expectedSavedNotesCount = 0
        actualSavedNotesCount = repository.notesList.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        var expectedNote: SimpleNote? = note1
        var actualNote = repository.deletedNote
        assertEquals(expectedNote, actualNote)

        deleteNoteUseCase.restoreNote()

        expectedNote = null
        actualNote = repository.deletedNote
        assertEquals(expectedNote, actualNote)

        expectedSavedNotesCount = 1
        actualSavedNotesCount = repository.notesList.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)
    }
}