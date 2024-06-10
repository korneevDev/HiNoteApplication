package ru.korneevdev.note.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class NoteCacheModel(
    val header: String,
    val text: String,
    @Embedded val noteColor: NoteColorCacheModel,
    @Embedded val timeStamp: TimeStampCacheModel
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

data class NoteColorCacheModel(
    val mainColor: Int,
    val buttonsColor: Int,
    val selectedColor: Int
)

data class TimeStampCacheModel(
    val createdTime: Long,
    val lastEditedTime: Long?
)