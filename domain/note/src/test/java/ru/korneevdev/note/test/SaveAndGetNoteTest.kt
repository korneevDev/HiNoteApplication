package ru.korneevdev.note.test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteTimeStamp
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.mock.TestConstants
import ru.korneevdev.note.mock.TestExceptionHandler
import ru.korneevdev.note.test_utils.TestNoteBuilder
import ru.korneevdev.note.mock.TestRepository
import ru.korneevdev.note.test_utils.TestTimeStampManager
import ru.korneevdev.note.use_case.GetNoteUseCase
import ru.korneevdev.note.use_case.SaveNoteUseCase

class SaveAndGetNoteTest {

    private val note1 = TestNoteBuilder
        .setTestFields(0)
        .buildSimpleNote()

    private val note2 = TestNoteBuilder
        .setTestFields(1)
        .buildSimpleNote()

    private val note3 = TestNoteBuilder
        .setTestFields(2)
        .buildSimpleNote()

    @Test
    fun saveNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val timeStampManager = TestTimeStampManager()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler,
                timeStampManager
            )

        val expected = ProcessingState.Created(0)
        val actualFlow = saveNoteUseCase.saveNote(note1)
        assertEquals(expected, actualFlow.first())

        val expectedSavedNotesCount = 1
        val actualSavedNotesCount = repository.notesList.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)
    }

    @Test
    fun saveAndGetNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val timeStampManager = TestTimeStampManager()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler,
                timeStampManager
            )
        val getNoteUseCase = GetNoteUseCase.Base(repository)

        val expectedState = ProcessingState.Created(0)
        val actualStateFlow = saveNoteUseCase.saveNote(note1)
        assertEquals(expectedState, actualStateFlow.first())

        val expectedNote = Note(0, NoteTimeStamp.Updated(10L, 20L), note1)
        val actualNoteFlow = getNoteUseCase.getNote(0)
        assertEquals(expectedNote, actualNoteFlow.first())
    }

    @Test
    fun saveAndGetMultiplesNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val timeStampManager = TestTimeStampManager()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler,
                timeStampManager
            )
        val getNoteUseCase = GetNoteUseCase.Base(repository)

        var expectedState = ProcessingState.Created(0)
        var actualStateFlow = saveNoteUseCase.saveNote(note1)
        assertEquals(expectedState, actualStateFlow.first())

        expectedState = ProcessingState.Created(1)
        actualStateFlow = saveNoteUseCase.saveNote(note2)
        assertEquals(expectedState, actualStateFlow.first())

        var expectedNote = Note(0, NoteTimeStamp.Updated(10L, 20L), note1)
        var actualNoteFlow = getNoteUseCase.getNote(0)
        assertEquals(expectedNote, actualNoteFlow.first())

        expectedNote = Note(1, NoteTimeStamp.Updated(10L, 20L), note2)
        actualNoteFlow = getNoteUseCase.getNote(1)
        assertEquals(expectedNote, actualNoteFlow.first())
    }

    @Test
    fun saveNoteOutOfMemoryException() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val timeStampManager = TestTimeStampManager()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler,
                timeStampManager
            )

        saveNoteUseCase.saveNote(note1)
        saveNoteUseCase.saveNote(note2)

        val expectedErrorState = ProcessingState.Error(TestConstants.errorOutOfMemory, 0)
        val actualErrorState = saveNoteUseCase.saveNote(note3).first()

        assertEquals(expectedErrorState, actualErrorState)
    }

}