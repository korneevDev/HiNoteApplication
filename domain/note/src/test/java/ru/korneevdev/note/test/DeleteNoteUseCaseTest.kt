package ru.korneevdev.note.test

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.korneevdev.entity.entity.ProcessingState
import ru.korneevdev.entity.entity.SimpleNote
import ru.korneevdev.note.mock.TestExceptionHandler
import ru.korneevdev.entity.test_utils.TestNoteBuilder
import ru.korneevdev.note.mock.TestRepository
import ru.korneevdev.entity.test_utils.TestTimeStampManager
import ru.korneevdev.note.use_case.DeleteNoteUseCase
import ru.korneevdev.note.use_case.SaveNoteUseCase

class DeleteNoteUseCaseTest {

    private val note1 = TestNoteBuilder
        .setTestFields(0)
        .buildSimpleNote()

    @Test
    fun saveAndDeleteNote() = runTest {
        val timeStampManager = TestTimeStampManager()
        val repository = TestRepository(timeStampManager)
        val exceptionHandler = TestExceptionHandler()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler,
                timeStampManager
            )
        val deleteNoteUseCase = DeleteNoteUseCase.Base(
            repository
        )

        var expected: ru.korneevdev.entity.entity.ProcessingState = ru.korneevdev.entity.entity.ProcessingState.Created(0)
        var actualFlow: Flow<ru.korneevdev.entity.entity.ProcessingState> = saveNoteUseCase.saveNote(note1)
        assertEquals(expected, actualFlow.first())

        var expectedSavedNotesCount = 1
        var actualSavedNotesCount = repository.notesList.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        expected = ru.korneevdev.entity.entity.ProcessingState.Deleted(0)
        actualFlow = deleteNoteUseCase.deleteNote(0)
        assertEquals(expected, actualFlow.first())

        expectedSavedNotesCount = 0
        actualSavedNotesCount = repository.notesList.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)
    }

    @Test
    fun deleteAndRestoreNote() = runTest {
        val timeStampManager = TestTimeStampManager()
        val repository = TestRepository(timeStampManager)
        val exceptionHandler = TestExceptionHandler()
        val saveNoteUseCase =
            SaveNoteUseCase.Base(
                repository,
                exceptionHandler,
                timeStampManager
            )
        val deleteNoteUseCase = DeleteNoteUseCase.Base(
            repository
        )

        var expected: ru.korneevdev.entity.entity.ProcessingState = ru.korneevdev.entity.entity.ProcessingState.Created(0)
        var actualFlow: Flow<ru.korneevdev.entity.entity.ProcessingState> = saveNoteUseCase.saveNote(note1)
        assertEquals(expected, actualFlow.first())

        var expectedSavedNotesCount = 1
        var actualSavedNotesCount = repository.notesList.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        expected = ru.korneevdev.entity.entity.ProcessingState.Deleted(0)
        actualFlow = deleteNoteUseCase.deleteNote(0)
        assertEquals(expected, actualFlow.first())

        expectedSavedNotesCount = 0
        actualSavedNotesCount = repository.notesList.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        var expectedNote: ru.korneevdev.entity.entity.SimpleNote? = note1
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