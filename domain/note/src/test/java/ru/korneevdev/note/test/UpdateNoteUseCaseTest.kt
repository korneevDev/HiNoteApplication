package ru.korneevdev.note.test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.korneevdev.note.entity.NoteColor
import ru.korneevdev.note.entity.NoteContent
import ru.korneevdev.note.entity.NoteHeader
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.entity.SimpleNote
import ru.korneevdev.note.mock.TestConstants
import ru.korneevdev.note.mock.TestExceptionHandler
import ru.korneevdev.note.mock.TestRepository
import ru.korneevdev.note.mock.TestStringResourceProvider
import ru.korneevdev.note.use_case.SaveNoteUseCase
import ru.korneevdev.note.use_case.UpdateNoteUseCase

class UpdateNoteUseCaseTest {

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

    @Test
    fun saveAndUpdateNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val resourceProvider = TestStringResourceProvider()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler
            )

        val updateNoteUseCase = UpdateNoteUseCase.Base(
            repository,
            repository,
            exceptionHandler,
            resourceProvider
        )

        var expected = ProcessingState.Created(0)
        var actualFlow = saveNoteUseCase.saveNote(note1)

        assertEquals(expected, actualFlow.first())


        var expectedSavedNotesCount = 1
        var actualSavedNotesCount = repository.notesList.size

        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)


        expected = ProcessingState.Created(1)
        actualFlow = updateNoteUseCase.updateNote(0, note2)

        assertEquals(expected, actualFlow.first())


        expectedSavedNotesCount = 2
        actualSavedNotesCount = repository.notesList.size

        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)
    }


    @Test
    fun saveAndUpdateNoteWithoutChanges() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val resourceProvider = TestStringResourceProvider()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler
            )

        val updateNoteUseCase = UpdateNoteUseCase.Base(
            repository,
            repository,
            exceptionHandler,
            resourceProvider
        )

        var expected: ProcessingState = ProcessingState.Created(0)
        var actualFlow = saveNoteUseCase.saveNote(note1)

        assertEquals(expected, actualFlow.first())


        var expectedSavedNotesCount = 1
        var actualSavedNotesCount = repository.notesList.size

        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)


        expected = ProcessingState.Error(TestConstants.errorNoChanges, 1)
        actualFlow = updateNoteUseCase.updateNote(0, note1)

        assertEquals(expected, actualFlow.first())


        expectedSavedNotesCount = 1
        actualSavedNotesCount = repository.notesList.size

        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)
    }

    @Test
    fun saveAndUpdateNoteWithOutOfMemoryException() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val resourceProvider = TestStringResourceProvider()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler
            )

        val updateNoteUseCase = UpdateNoteUseCase.Base(
            repository,
            repository,
            exceptionHandler,
            resourceProvider
        )

        var expected: ProcessingState = ProcessingState.Created(0)
        var actualFlow = saveNoteUseCase.saveNote(note1)

        assertEquals(expected, actualFlow.first())


        var expectedSavedNotesCount = 1
        var actualSavedNotesCount = repository.notesList.size

        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)


        expected = ProcessingState.Created(1)
        actualFlow = updateNoteUseCase.updateNote(0, note2)

        assertEquals(expected, actualFlow.first())


        expectedSavedNotesCount = 2
        actualSavedNotesCount = repository.notesList.size

        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        expected = ProcessingState.Error(TestConstants.errorOutOfMemory, 0)
        actualFlow = updateNoteUseCase.updateNote(1, note1)

        assertEquals(expected, actualFlow.first())


        expectedSavedNotesCount = 2
        actualSavedNotesCount = repository.notesList.size

        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)
    }
}

