package ru.korneevdev.note

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.korneevdev.entity.entity.Note
import ru.korneevdev.entity.entity.NoteTimeStamp
import ru.korneevdev.entity.entity.SimpleNote
import ru.korneevdev.entity.mapper.NoteMapper
import ru.korneevdev.room.room.MapperToNoteCacheModel
import ru.korneevdev.room.room.NoteCacheModel
import ru.korneevdev.room.room.NoteDAO

interface NoteCacheDataSource {

    suspend fun getNote(id: Int): Flow<Note>

    suspend fun saveNote(note: SimpleNote, timeStamp: NoteTimeStamp): Flow<Int>

    suspend fun saveNote(note: Note): Flow<Int>

    suspend fun deleteNote(id: Int): Note

    class Base(
        private val dao: NoteDAO,
        private val mapperToDB: MapperToNoteCacheModel,
        private val mapperFromDB: NoteMapper<Note>
    ) : NoteCacheDataSource {
        override suspend fun getNote(id: Int) = dao.getNote(id).map {
            it.map(mapperFromDB)
        }

        override suspend fun saveNote(note: SimpleNote, timeStamp: NoteTimeStamp) =
            flow {
                emit(
                    dao.saveNote(
                        NoteCacheModel(
                            note.map(mapperToDB),
                            timeStamp.map(mapperToDB)
                        )
                    ).toInt()
                )
            }


        override suspend fun saveNote(note: Note) = flow {
            emit(dao.saveNote(note.map(mapperToDB)).toInt())
        }

        override suspend fun deleteNote(id: Int) =
            dao.getNote(id).first().also {
                dao.deleteNote(it)
            }.map(mapperFromDB)
    }
}