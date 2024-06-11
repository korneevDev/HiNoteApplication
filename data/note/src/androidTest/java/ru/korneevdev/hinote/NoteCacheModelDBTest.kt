package ru.korneevdev.hinote

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.korneevdev.note.mapperImplementation.MapperToNoteCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToNoteColorCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToNoteContentCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToNoteHeaderCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToSimpleNoteCacheModel
import ru.korneevdev.note.mapperImplementation.MapperToTimeStampCacheModel
import ru.korneevdev.note.room.NoteDAO
import ru.korneevdev.note.room.NoteDB
import ru.korneevdev.note.test_utils.TestNoteBuilder
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class NoteCacheModelDBTest {
    private lateinit var noteDao: NoteDAO
    private lateinit var db: NoteDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, NoteDB::class.java
        ).build()
        noteDao = db.getNoteDAO()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun saveNotesTest() {
        val mapperToDB =
            MapperToNoteCacheModel(
                MapperToTimeStampCacheModel(),
                MapperToSimpleNoteCacheModel(
                    MapperToNoteHeaderCacheModel(),
                    MapperToNoteContentCacheModel(),
                    MapperToNoteColorCacheModel()
                )
            )

        val note1 = TestNoteBuilder.setTestFields(1).buildNote()
        val note1DB = note1.map(mapperToDB).also {
            it.id = 0
        }

        val note2 = TestNoteBuilder.setTestFields(4).buildNote()
        val note2DB = note2.map(mapperToDB).also {
            it.id = 0
        }

        var expected: Any = 1L
        var actual: Any = noteDao.saveNote(note1DB)
        assertEquals(expected, actual)

        expected = 2L
        actual = noteDao.saveNote(note2DB)
        assertEquals(expected, actual)

    }

    @Test
    @Throws(Exception::class)
    fun saveAndGetNotesTest() = runBlocking {
        val mapperToDB =
            MapperToNoteCacheModel(
                MapperToTimeStampCacheModel(),
                MapperToSimpleNoteCacheModel(
                    MapperToNoteHeaderCacheModel(),
                    MapperToNoteContentCacheModel(),
                    MapperToNoteColorCacheModel()
                )
            )

        val note1 = TestNoteBuilder.setTestFields(5).buildNote()
        val note1DB = note1.map(mapperToDB).also {
            it.id = 0
        }

        val note2 = TestNoteBuilder.setTestFields(6).buildNote()
        val note2DB = note2.map(mapperToDB).also {
            it.id = 0
        }

        noteDao.saveNote(note1DB).also {
            note1DB.id = it.toInt()
        }

        noteDao.saveNote(note2DB).also {
            note2DB.id = it.toInt()
        }

        var expected: Any = note1DB
        var actual: Any = noteDao.getNote(note1DB.id)
        assertEquals(expected, (actual as Flow<*>).first())

        expected = note2DB
        actual = noteDao.getNote(note2DB.id)
        assertEquals(expected, (actual as Flow<*>).first())
    }

    @Test
    @Throws(Exception::class)
    fun saveAndDeleteNoteTest(): Unit = runBlocking {
        val mapperToDB =
            MapperToNoteCacheModel(
                MapperToTimeStampCacheModel(),
                MapperToSimpleNoteCacheModel(
                    MapperToNoteHeaderCacheModel(),
                    MapperToNoteContentCacheModel(),
                    MapperToNoteColorCacheModel()
                )
            )

        val note1 = TestNoteBuilder.setTestFields(5).buildNote()
        val note1DB = note1.map(mapperToDB).also {
            it.id = 0
        }

        val note2 = TestNoteBuilder.setTestFields(6).buildNote()
        val note2DB = note2.map(mapperToDB).also {
            it.id = 0
        }

        noteDao.saveNote(note1DB).also {
            note1DB.id = it.toInt()
        }

        noteDao.saveNote(note2DB).also {
            note2DB.id = it.toInt()
        }

        var expected: Any? = note1DB
        var actual: Any? = noteDao.getNote(note1DB.id)
        assertEquals(expected, (actual as Flow<*>).first())

        expected = note2DB
        actual = noteDao.getNote(note2DB.id)
        assertEquals(expected, (actual as Flow<*>).first())

        noteDao.deleteNote(note1DB)

        expected = note2DB
        actual = noteDao.getNote(note2DB.id)
        assertEquals(expected, (actual as Flow<*>).first())

        expected = null
        actual = noteDao.getNote(note1DB.id)
        assertEquals(expected, (actual as Flow<*>).first())
    }
}