package ru.korneevdev.note.room

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.korneevdev.note.entity.NoteColorMapped
import ru.korneevdev.note.entity.NoteContentMapped
import ru.korneevdev.note.entity.NoteHeaderMapped
import ru.korneevdev.note.entity.NoteMapped
import ru.korneevdev.note.entity.NoteTimeStampMapped
import ru.korneevdev.note.entity.SimpleNoteMapped
import ru.korneevdev.note.mapper.NoteColorMapper
import ru.korneevdev.note.mapper.NoteContentMapper
import ru.korneevdev.note.mapper.NoteHeaderMapper
import ru.korneevdev.note.mapper.NoteMapper
import ru.korneevdev.note.mapper.NoteTimeStampMapper
import ru.korneevdev.note.mapper.SimpleNoteMapper

@Entity(tableName = "note")
data class NoteCacheModel(
    @Embedded val simpleNote: SimpleNoteCacheModel,
    @Embedded val timeStamp: TimeStampCacheModel
) : NoteMapped {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    override fun <T> map(mapper: NoteMapper<T>) = mapper.map(id, timeStamp, simpleNote)
}

data class SimpleNoteCacheModel(
    @Embedded(prefix = "header") val header: NoteHeaderCacheModel,
    @Embedded(prefix = "content") val text: NoteContentCacheModel,
    @Embedded(prefix = "color") val noteColor: NoteColorCacheModel,
) : SimpleNoteMapped {
    override fun <T> map(mapper: SimpleNoteMapper<T>) = mapper.map(header, text, noteColor)
}

data class NoteColorCacheModel(
    val mainColor: Int,
    val buttonsColor: Int,
    val selectedColor: Int
) : NoteColorMapped {
    override fun <T> map(mapper: NoteColorMapper<T>) =
        mapper.map(mainColor, buttonsColor, selectedColor)
}

data class TimeStampCacheModel(
    val createdTime: Long,
    val lastEditedTime: Long?
) : NoteTimeStampMapped {
    override fun <T> map(mapper: NoteTimeStampMapper<T>) =
        if (lastEditedTime == null)
            mapper.mapFirstCreatedTimeStamp(createdTime)
        else
            mapper.mapUpdatedTimeStamp(createdTime, lastEditedTime)
}

data class NoteHeaderCacheModel(
    val text: String
) : NoteHeaderMapped {
    override fun <T> map(mapper: NoteHeaderMapper<T>) = mapper.map(text)
}

data class NoteContentCacheModel(
    val text: String
) : NoteContentMapped {
    override fun <T> map(mapper: NoteContentMapper<T>) = mapper.map(text)
}