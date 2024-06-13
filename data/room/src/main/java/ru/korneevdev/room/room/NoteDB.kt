package ru.korneevdev.room.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [NoteCacheModel::class],
    exportSchema = false
)
abstract class NoteDB : RoomDatabase() {

    abstract fun getNoteDAO(): NoteDAO

}