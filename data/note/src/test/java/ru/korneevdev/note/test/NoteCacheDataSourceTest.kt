package ru.korneevdev.note.test

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import ru.korneevdev.note.NoteCacheDataSource
import ru.korneevdev.room.room.mapperImplementation.MapperToNoteCacheModel
import ru.korneevdev.room.room.mapperImplementation.MapperToNoteColor
import ru.korneevdev.room.room.mapperImplementation.MapperToNoteColorCacheModel
import ru.korneevdev.room.room.mapperImplementation.MapperToNoteContent
import ru.korneevdev.room.room.mapperImplementation.MapperToNoteContentCacheModel
import ru.korneevdev.room.room.mapperImplementation.MapperToNoteHeader
import ru.korneevdev.room.room.mapperImplementation.MapperToNoteHeaderCacheModel
import ru.korneevdev.room.room.mapperImplementation.MapperToNoteModel
import ru.korneevdev.room.room.mapperImplementation.MapperToSimpleNoteCacheModel
import ru.korneevdev.room.room.mapperImplementation.MapperToSimpleNoteModel
import ru.korneevdev.room.room.mapperImplementation.MapperToTimeStampCacheModel
import ru.korneevdev.room.room.mapperImplementation.MapperToTimeStampModel
import ru.korneevdev.note.mock.TestCacheDAO
import ru.korneevdev.entity.test_utils.TestNoteBuilder
import ru.korneevdev.entity.test_utils.TestTimeStampManager

class NoteCacheDataSourceTest {

    @Test
    fun saveAndGetNote() = runTest {
        val dao = TestCacheDAO()

        val dataSource = NoteCacheDataSource.Base(
            dao,
            ru.korneevdev.room.room.mapperImplementation.MapperToNoteCacheModel(
                ru.korneevdev.room.room.mapperImplementation.MapperToTimeStampCacheModel(),
                ru.korneevdev.room.room.mapperImplementation.MapperToSimpleNoteCacheModel(
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteHeaderCacheModel(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteContentCacheModel(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteColorCacheModel()
                )
            ),
            ru.korneevdev.room.room.mapperImplementation.MapperToNoteModel(
                ru.korneevdev.room.room.mapperImplementation.MapperToTimeStampModel(),
                ru.korneevdev.room.room.mapperImplementation.MapperToSimpleNoteModel(
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteHeader(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteContent(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteColor()
                )
            )
        )

        val timeStampManager = ru.korneevdev.entity.test_utils.TestTimeStampManager()
        val note1 = ru.korneevdev.entity.test_utils.TestNoteBuilder.setTestFields(0).buildSimpleNote()

        var expected: Any = 0
        var actual: Any = dataSource.saveNote(note1, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, (actual as Flow<*>).first())

        expected = ru.korneevdev.entity.test_utils.TestNoteBuilder.setTestFields(0).buildNote()
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
            ru.korneevdev.room.room.mapperImplementation.MapperToNoteCacheModel(
                ru.korneevdev.room.room.mapperImplementation.MapperToTimeStampCacheModel(),
                ru.korneevdev.room.room.mapperImplementation.MapperToSimpleNoteCacheModel(
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteHeaderCacheModel(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteContentCacheModel(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteColorCacheModel()
                )
            ),
            ru.korneevdev.room.room.mapperImplementation.MapperToNoteModel(
                ru.korneevdev.room.room.mapperImplementation.MapperToTimeStampModel(),
                ru.korneevdev.room.room.mapperImplementation.MapperToSimpleNoteModel(
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteHeader(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteContent(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteColor()
                )
            )
        )

        val timeStampManager = ru.korneevdev.entity.test_utils.TestTimeStampManager()
        val note1 = ru.korneevdev.entity.test_utils.TestNoteBuilder.setTestFields(0).buildSimpleNote()

        var expected: Any = 0
        var actual: Any = dataSource.saveNote(note1, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, (actual as Flow<*>).first())

        expected = 1
        actual = dao.notes.size
        assertEquals(expected, actual)

        expected = ru.korneevdev.entity.test_utils.TestNoteBuilder.setTestFields(0).buildNote()
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
            ru.korneevdev.room.room.mapperImplementation.MapperToNoteCacheModel(
                ru.korneevdev.room.room.mapperImplementation.MapperToTimeStampCacheModel(),
                ru.korneevdev.room.room.mapperImplementation.MapperToSimpleNoteCacheModel(
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteHeaderCacheModel(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteContentCacheModel(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteColorCacheModel()
                )
            ),
            ru.korneevdev.room.room.mapperImplementation.MapperToNoteModel(
                ru.korneevdev.room.room.mapperImplementation.MapperToTimeStampModel(),
                ru.korneevdev.room.room.mapperImplementation.MapperToSimpleNoteModel(
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteHeader(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteContent(),
                    ru.korneevdev.room.room.mapperImplementation.MapperToNoteColor()
                )
            )
        )

        val timeStampManager = ru.korneevdev.entity.test_utils.TestTimeStampManager()
        val note1 = ru.korneevdev.entity.test_utils.TestNoteBuilder.setTestFields(0).buildSimpleNote()

        var expected: Any = 0
        var actual: Any = dataSource.saveNote(note1, timeStampManager.getCurrentTimeStamp())
        assertEquals(expected, (actual as Flow<*>).first())

        expected = 1
        actual = dao.notes.size
        assertEquals(expected, actual)

        expected = ru.korneevdev.entity.test_utils.TestNoteBuilder.setTestFields(0).buildNote()
        actual = dataSource.deleteNote(0)
        assertEquals(expected, actual)

        expected = 0
        actual = dao.notes.size
        assertEquals(expected, actual)

        expected = 0
        actual = dataSource.saveNote(ru.korneevdev.entity.test_utils.TestNoteBuilder.setTestFields(0).buildNote())
        assertEquals(expected, (actual as Flow<*>).first())

    }
}