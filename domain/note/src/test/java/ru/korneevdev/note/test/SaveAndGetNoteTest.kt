package ru.korneevdev.note.test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteColor
import ru.korneevdev.note.entity.NoteContent
import ru.korneevdev.note.entity.NoteHeader
import ru.korneevdev.note.entity.NoteTimeStamp
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.entity.SimpleNote
import ru.korneevdev.note.mock.TestConstants
import ru.korneevdev.note.mock.TestExceptionHandler
import ru.korneevdev.note.mock.TestRepository
import ru.korneevdev.note.use_case.GetNoteUseCase
import ru.korneevdev.note.use_case.SaveNoteUseCase

class SaveAndGetNoteTest {

    private val note1 = SimpleNote(
        NoteHeader("Test note header 0"),
        NoteContent("Test note content 1"),
        NoteColor(0)
    )

    private val note2 = SimpleNote(
        NoteHeader("Test note header 1"),
        NoteContent("Test note content 2"),
        NoteColor(0)
    )

    private val note3 = SimpleNote(
        NoteHeader("Test note header 2"),
        NoteContent("Test note content 3"),
        NoteColor(0)
    )
    @Test
    fun saveNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler
            )

        val expected = ProcessingState.Success(0)
        val actualFlow = saveNoteUseCase.saveNote(note1)

        actualFlow.collect{actual ->
            assertEquals(expected, actual)
        }

        val expectedSavedNotesCount = 1
        val actualSavedNotesCount = repository.notesList.size

        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

    }

    @Test
    fun saveAndGetNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler
            )

        val getNoteUseCase = GetNoteUseCase.Base(repository)

        val expectedState = ProcessingState.Success(0)
        val actualStateFlow = saveNoteUseCase.saveNote(note1)

        actualStateFlow.collect{ actual ->
            assertEquals(expectedState, actual)
        }

        val expectedNote = Note(0, NoteTimeStamp(10L, 20L), note1)
        val actualNoteFlow = getNoteUseCase.getNote(0)

        actualNoteFlow.collect{actual ->
            assertEquals(expectedNote, actual)
        }
    }

    @Test
    fun saveAndGetMultiplesNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler
            )

        val getNoteUseCase = GetNoteUseCase.Base(repository)

        var expectedState = ProcessingState.Success(0)
        var actualStateFlow = saveNoteUseCase.saveNote(note1)

        actualStateFlow.collect{ actual ->
            assertEquals(expectedState, actual)
        }

        expectedState = ProcessingState.Success(1)
        actualStateFlow = saveNoteUseCase.saveNote(note2)

        actualStateFlow.collect{ actual ->
            assertEquals(expectedState, actual)
        }

        var expectedNote = Note(0, NoteTimeStamp(10L, 20L), note1)
        var actualNoteFlow = getNoteUseCase.getNote(0)

        actualNoteFlow.collect{actual ->
            assertEquals(expectedNote, actual)
        }

        expectedNote = Note(1, NoteTimeStamp(10L, 20L), note2)
        actualNoteFlow = getNoteUseCase.getNote(1)

        actualNoteFlow.collect{actual ->
            assertEquals(expectedNote, actual)
        }
    }

    @Test
    fun saveNoteOutOfMemoryException() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler
            )

        saveNoteUseCase.saveNote(note1)
        saveNoteUseCase.saveNote(note2)

        val expectedErrorState = ProcessingState.Error(TestConstants.errorOutOfMemory, 0)
        val actualErrorState =  saveNoteUseCase.saveNote(note3).first()

        assertEquals(expectedErrorState, actualErrorState)
    }

}