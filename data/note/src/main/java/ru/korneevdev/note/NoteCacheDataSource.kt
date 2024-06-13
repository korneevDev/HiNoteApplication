package ru.korneevdev.note

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import ru.korneevdev.entity.entity.Note

interface NoteCacheDataSource {

    suspend fun getNote(id: Int): Flow<Note>

    suspend fun saveNote(note: ru.korneevdev.entity.entity.SimpleNote, timeStamp: ru.korneevdev.entity.entity.NoteTimeStamp): Flow<Int>

    suspend fun saveNote(note: Note): Flow<Int>

    suspend fun deleteNote(id: Int): Note

    class Base(
        private val dao: ru.korneevdev.room.room.NoteDAO,
        private val mapperToDB: ru.korneevdev.room.room.mapperImplementation.MapperToNoteCacheModel,
        private val mapperFromDB: ru.korneevdev.entity.mapper.NoteMapper<Note>
    ) : NoteCacheDataSource {
        override suspend fun getNote(id: Int) = dao.getNote(id).map {
            it.map(mapperFromDB)
        }

        override suspend fun saveNote(note: ru.korneevdev.entity.entity.SimpleNote, timeStamp: ru.korneevdev.entity.entity.NoteTimeStamp) =
            flow {
                emit(
                    dao.saveNote(
                        ru.korneevdev.room.room.NoteCacheModel(
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