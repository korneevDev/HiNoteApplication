package ru.korneevdev.note.test

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.korneevdev.entity.test_utils.TestNoteBuilder
import ru.korneevdev.entity.test_utils.TestTimeStampManager
import ru.korneevdev.note.MapperToNoteColor
import ru.korneevdev.note.MapperToNoteContent
import ru.korneevdev.note.MapperToNoteHeader
import ru.korneevdev.note.MapperToNoteModel
import ru.korneevdev.note.MapperToSimpleNoteModel
import ru.korneevdev.note.MapperToTimeStampModel
import ru.korneevdev.note.NoteCacheDataSource
import ru.korneevdev.note.mock.TestCacheDAO
import ru.korneevdev.room.room.MapperToNoteCacheModel
import ru.korneevdev.room.room.MapperToNoteColorCacheModel
import ru.korneevdev.room.room.MapperToNoteContentCacheModel
import ru.korneevdev.room.room.MapperToNoteHeaderCacheModel
import ru.korneevdev.room.room.MapperToSimpleNoteCacheModel
import ru.korneevdev.room.room.MapperToTimeStampCacheModel

class NoteCacheDataSourceTest {

    @Test
    fun saveAndGetNote() = runTest {
        val dao = TestCacheDAO()

        val dataSource = NoteCacheDataSource.Base(
            dao,
            MapperToNoteCacheModel(
                MapperToTimeStampCacheModel(),
                MapperToSimpleNoteCacheModel(
                    MapperToNoteHeaderCacheModel(),
                    MapperToNoteContentCacheModel(),
                    MapperToNoteColorCacheModel()
                )
            ),
            MapperToNoteModel(
                MapperToTimeStampModel(),
                MapperToSimpleNoteModel(
                    MapperToNoteHeader(),
                    MapperToNoteContent(),
                    MapperToNoteColor()
                )
            )
        )

        val timeStampManager = TestTimeStampManager()
        val note1 = TestNoteBuilder.setTestFields(0).buildSimpleNote()

        var expected: Any = 0
        var actual: Any = dataSource.saveNote(note1, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, (actual as Flow<*>).first())

        expected = TestNoteBuilder.setTestFields(0).buildNote()
        actual = dataSource.getNote(0)
        assertEquals(expected, (actual as Flow<*>).first())

        expected = 1
        actual = dao.notes.size
        assertEquals(expected, actual)
    }

    @Test
    fun saveAndDeleteNote() = runTest {
        val dao = TestCacheDAO()

        val dataSource = NoteCacheDataSource.Base(
            dao,
            MapperToNoteCacheModel(
                MapperToTimeStampCacheModel(),
                MapperToSimpleNoteCacheModel(
                    MapperToNoteHeaderCacheModel(),
                    MapperToNoteContentCacheModel(),
                    MapperToNoteColorCacheModel()
                )
            ),
            MapperToNoteModel(
                MapperToTimeStampModel(),
                MapperToSimpleNoteModel(
                    MapperToNoteHeader(),
                    MapperToNoteContent(),
                    MapperToNoteColor()
                )
            )
        )

        val timeStampManager = TestTimeStampManager()
        val note1 = TestNoteBuilder.setTestFields(0).buildSimpleNote()

        var expected: Any = 0
        var actual: Any = dataSource.saveNote(note1, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, (actual as Flow<*>).first())

        expected = 1
        actual = dao.notes.size
        assertEquals(expected, actual)

        expected = TestNoteBuilder.setTestFields(0).buildNote()
        actual = dataSource.deleteNote(0)
        assertEquals(expected, actual)

        expected = 0
        actual = dao.notes.size
        assertEquals(expected, actual)
    }

    @Test
    fun saveDeleteAndRestoreNote() = runTest {
        val dao = TestCacheDAO()

        val dataSource = NoteCacheDataSource.Base(
            dao,
            MapperToNoteCacheModel(
                MapperToTimeStampCacheModel(),
                MapperToSimpleNoteCacheModel(
                    MapperToNoteHeaderCacheModel(),
                    MapperToNoteContentCacheModel(),
                    MapperToNoteColorCacheModel()
                )
            ),
            MapperToNoteModel(
                MapperToTimeStampModel(),
                MapperToSimpleNoteModel(
                    MapperToNoteHeader(),
                    MapperToNoteContent(),
                    MapperToNoteColor()
                )
            )
        )

        val timeStampManager = TestTimeStampManager()
        val note1 = TestNoteBuilder.setTestFields(0).buildSimpleNote()

        var expected: Any = 0
        var actual: Any = dataSource.saveNote(note1, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, (actual as Flow<*>).first())

        expected = 1
        actual = dao.notes.size
        assertEquals(expected, actual)

        expected = TestNoteBuilder.setTestFields(0).buildNote()
        actual = dataSource.deleteNote(0)
        assertEquals(expected, actual)

        expected = 0
        actual = dao.notes.size
        assertEquals(expected, actual)

        expected = 0
        actual = dataSource.saveNote(TestNoteBuilder.setTestFields(0).buildNote())
        assertEquals(expected, (actual as Flow<*>).first())

    }
}