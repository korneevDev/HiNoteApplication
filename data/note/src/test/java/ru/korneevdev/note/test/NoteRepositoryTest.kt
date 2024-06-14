package ru.korneevdev.note.test

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.korneevdev.entity.noteEntity.ProcessingState
import ru.korneevdev.entity.test_utils.TestNoteBuilder
import ru.korneevdev.entity.test_utils.TestTimeStampManager
import ru.korneevdev.note.NoteRepositoryImplementation
import ru.korneevdev.note.mock.TestCacheDataSource
import ru.korneevdev.note.mock.TestDispatcherManager

class NoteRepositoryTest {

    private val dispatcher = StandardTestDispatcher()

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
    fun saveAndUpdateNote() = runTest(dispatcher) {
        val dataSource = TestCacheDataSource()
        val dispatcherManager = TestDispatcherManager(dispatcher)
        val timeStampManager = TestTimeStampManager()

        val repository =
            NoteRepositoryImplementation(dataSource, dispatcherManager)

        var expected = ProcessingState.Created(0)
        var actualFlow = repository.saveNote(note1, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, actualFlow.first())

        timeStampManager.time = 1

        expected = ProcessingState.Created(1)
        actualFlow = repository.saveNote(note2, timeStampManager.getCurrentTimeLong(), 0)
        assertEquals(expected, actualFlow.first())

        var expectedNote = TestNoteBuilder
            .setTestFields(1)
            .setUpdatedTimeStamp(0, 1)
            .buildNote()
        var actualNote = repository.getNote(1)
        assertEquals(expectedNote, actualNote.first())

        var expectedSavedNotesCount = 2
        var actualSavedNotesCount = dataSource.notes.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        timeStampManager.time = 2

        expected = ProcessingState.Created(2)
        actualFlow = repository.saveNote(note3, timeStampManager.getCurrentTimeLong(), 1)
        assertEquals(expected, actualFlow.first())

        expectedNote = TestNoteBuilder
            .setTestFields(2)
            .setUpdatedTimeStamp(1, 2)
            .buildNote()
        actualNote = repository.getNote(2)
        assertEquals(expectedNote, actualNote.first())

        expectedSavedNotesCount = 3
        actualSavedNotesCount = dataSource.notes.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)
    }

    @Test
    fun saveAndGetNote() = runTest(dispatcher) {
        val dataSource = TestCacheDataSource()
        val dispatcherManager = TestDispatcherManager(dispatcher)
        val timeStampManager = TestTimeStampManager()

        val repository = NoteRepositoryImplementation(dataSource, dispatcherManager)

        val expected = ProcessingState.Created(0)
        val actualFlow = repository.saveNote(note1, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, actualFlow.first())

        val expectedSavedNotesCount = 1
        val actualSavedNotesCount = dataSource.notes.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        val expectedNote = TestNoteBuilder.setTestFields(0).buildNote()
        val actualNote = repository.getNote(0)
        assertEquals(expectedNote, actualNote.first())
    }

    @Test
    fun saveAndDeleteNote() = runTest(dispatcher) {
        val dataSource = TestCacheDataSource()
        val dispatcherManager = TestDispatcherManager(dispatcher)
        val timeStampManager = TestTimeStampManager()

        val repository = NoteRepositoryImplementation(dataSource, dispatcherManager)

        var expected = ProcessingState.Created(0)
        var actualFlow = repository.saveNote(note1, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, actualFlow.first())

        var expectedSavedNotesCount = 1
        var actualSavedNotesCount = dataSource.notes.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        val expectedNote = TestNoteBuilder.setTestFields(0).buildNote()
        val actualNote = repository.getNote(0)
        assertEquals(expectedNote, actualNote.first())

        expected = ProcessingState.Created(1)
        actualFlow = repository.saveNote(note2, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, actualFlow.first())

        expectedSavedNotesCount = 2
        actualSavedNotesCount = dataSource.notes.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        repository.deleteNote(1)

        expectedSavedNotesCount = 1
        actualSavedNotesCount = dataSource.notes.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)
    }

    @Test
    fun saveDeleteAndRestoreNote() = runTest(dispatcher) {
        val dataSource = TestCacheDataSource()
        val dispatcherManager = TestDispatcherManager(dispatcher)
        val timeStampManager = TestTimeStampManager()

        val repository = NoteRepositoryImplementation(dataSource, dispatcherManager)

        var expected = ProcessingState.Created(0)
        var actualFlow = repository.saveNote(note1, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, actualFlow.first())

        var expectedSavedNotesCount = 1
        var actualSavedNotesCount = dataSource.notes.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        var expectedNote = TestNoteBuilder.setTestFields(0).buildNote()
        var actualNote = repository.getNote(0)
        assertEquals(expectedNote, actualNote.first())

        expected = ProcessingState.Created(1)
        actualFlow = repository.saveNote(note2, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, actualFlow.first())

        expectedSavedNotesCount = 2
        actualSavedNotesCount = dataSource.notes.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        repository.deleteNote(1)

        expectedSavedNotesCount = 1
        actualSavedNotesCount = dataSource.notes.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        repository.restoreDeletedNote()

        expectedSavedNotesCount = 2
        actualSavedNotesCount = dataSource.notes.size
        assertEquals(expectedSavedNotesCount, actualSavedNotesCount)

        expectedNote = TestNoteBuilder.setTestFields(1).buildNote()
        actualNote = repository.getNote(1)
        assertEquals(expectedNote, actualNote.first())
    }

}