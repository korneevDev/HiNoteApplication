package ru.korneevdev.note.test

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.korneevdev.note.NoteCacheDataSource
import ru.korneevdev.note.mapperImplementation.MapperToNoteCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToNoteColor
import ru.korneevdev.note.mapperImplementation.MapperToNoteColorCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToNoteContent
import ru.korneevdev.note.mapperImplementation.MapperToNoteContentCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToNoteHeader
import ru.korneevdev.note.mapperImplementation.MapperToNoteHeaderCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToNoteModel
import ru.korneevdev.note.mapperImplementation.MapperToSimpleNoteCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToSimpleNoteModel
import ru.korneevdev.note.mapperImplementation.MapperToTimeStampCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToTimeStampModel
import ru.korneevdev.note.mock.TestCacheDAO
import ru.korneevdev.note.test_utils.TestNoteBuilder
import ru.korneevdev.note.test_utils.TestTimeStampManager

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