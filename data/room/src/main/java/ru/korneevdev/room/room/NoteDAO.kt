package ru.korneevdev.room.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDAO {

    @Query("SELECT * FROM note WHERE id =:id")
    fun getNote(id: Int) : Flow<NoteCacheModel>

    @Insert
    fun saveNote(note: NoteCacheModel) : Long

    @Delete
    fun deleteNote(note: NoteCacheModel)
}