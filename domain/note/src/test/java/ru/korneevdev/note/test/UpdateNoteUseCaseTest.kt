package ru.korneevdev.note.test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.mock.TestConstants
import ru.korneevdev.note.mock.TestExceptionHandler
import ru.korneevdev.note.test_utils.TestNoteBuilder
import ru.korneevdev.note.mock.TestRepository
import ru.korneevdev.note.mock.TestStringResourceProvider
import ru.korneevdev.note.test_utils.TestTimeStampManager
import ru.korneevdev.note.use_case.GetNoteUseCase
import ru.korneevdev.note.use_case.SaveNoteUseCase
import ru.korneevdev.note.use_case.UpdateNoteUseCase

class UpdateNoteUseCaseTest {

    private val note1 = TestNoteBuilder()
        .setTestFields(0)
        .buildSimpleNote()

    private val note2 = TestNoteBuilder()
        .setTestFields(1)
        .buildSimpleNote()

    @Test
    fun saveAndUpdateNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val resourceProvider = TestStringResourceProvider()
        val timeStampManager = TestTimeStampManager()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler,
                timeStampManager
            )
        val updateNoteUseCase = UpdateNoteUseCase.Base(
            repository,
            repository,
            exceptionHandler,
            resourceProvider,
            timeStampManager
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
    fun saveUpdateGetNote() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val resourceProvider = TestStringResourceProvider()
        val timeStampManager = TestTimeStampManager()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler,
                timeStampManager
            )
        val updateNoteUseCase = UpdateNoteUseCase.Base(
            repository,
            repository,
            exceptionHandler,
            resourceProvider,
            timeStampManager
        )
        val getNoteUseCase = GetNoteUseCase.Base(
            repository
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

        val expectedNote = TestNoteBuilder()
            .setTestFields(1)
            .setUpdatedTimeStamp(0, 1)
            .buildNote()
        val actualNote = getNoteUseCase.getNote(1)
        assertEquals(expectedNote, actualNote.first())
    }

    @Test
    fun saveAndUpdateNoteWithoutChanges() = runTest {
        val repository = TestRepository()
        val exceptionHandler = TestExceptionHandler()
        val resourceProvider = TestStringResourceProvider()
        val timeStampManager = TestTimeStampManager()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler,
                timeStampManager
            )
        val updateNoteUseCase = UpdateNoteUseCase.Base(
            repository,
            repository,
            exceptionHandler,
            resourceProvider,
            timeStampManager
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
        val timeStampManager = TestTimeStampManager()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler,
                timeStampManager
            )
        val updateNoteUseCase = UpdateNoteUseCase.Base(
            repository,
            repository,
            exceptionHandler,
            resourceProvider,
            timeStampManager
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

