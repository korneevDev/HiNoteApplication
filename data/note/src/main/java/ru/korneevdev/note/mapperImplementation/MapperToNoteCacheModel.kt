package ru.korneevdev.note.mapperImplementation

import ru.korneevdev.note.entity.NoteColorMapped
import ru.korneevdev.note.entity.NoteContentMapped
import ru.korneevdev.note.entity.NoteHeaderMapped
import ru.korneevdev.note.entity.NoteTimeStampMapped
import ru.korneevdev.note.entity.SimpleNoteMapped
import ru.korneevdev.note.mapper.NoteColorMapper
import ru.korneevdev.note.mapper.NoteContentMapper
import ru.korneevdev.note.mapper.NoteHeaderMapper
import ru.korneevdev.note.mapper.NoteMapper
import ru.korneevdev.note.mapper.NoteTimeStampMapper
import ru.korneevdev.note.mapper.SimpleNoteMapper
import ru.korneevdev.note.room.NoteCacheModel
import ru.korneevdev.note.room.NoteColorCacheModel
import ru.korneevdev.note.room.NoteContentCacheModel
import ru.korneevdev.note.room.NoteHeaderCacheModel
import ru.korneevdev.note.room.SimpleNoteCacheModel
import ru.korneevdev.note.room.TimeStampCacheModel

class MapperToNoteCacheModel(
    private val timeStampMapper: NoteTimeStampMapper<TimeStampCacheModel>,
    private val simpleNoteMapper: SimpleNoteMapper<SimpleNoteCacheModel>
) : NoteMapper<NoteCacheModel>, SimpleNoteMapper<SimpleNoteCacheModel>,
    NoteTimeStampMapper<TimeStampCacheModel> {
    override fun map(
        id: Int,
        timeStamp: NoteTimeStampMapped,
        simpleNote: SimpleNoteMapped
    ) = NoteCacheModel(
        simpleNote.map(simpleNoteMapper),
        timeStamp.map(timeStampMapper)
    ).also {
        it.id = id
    }

    override fun map(
        header: NoteHeaderMapped,
        content: NoteContentMapped,
        color: NoteColorMapped
    ) = simpleNoteMapper.map(header, content, color)

    override fun mapFirstCreatedTimeStamp(createTime: Long) =
        timeStampMapper.mapFirstCreatedTimeStamp(createTime)

    override fun mapUpdatedTimeStamp(createTime: Long, lastEditedTime: Long) =
        timeStampMapper.mapUpdatedTimeStamp(createTime, lastEditedTime)
}

class MapperToTimeStampCacheModel : NoteTimeStampMapper<TimeStampCacheModel> {
    override fun mapFirstCreatedTimeStamp(createTime: Long) =
        TimeStampCacheModel(createTime, null)

    override fun mapUpdatedTimeStamp(createTime: Long, lastEditedTime: Long) =
        TimeStampCacheModel(createTime, lastEditedTime)
}

class MapperToSimpleNoteCacheModel(
    private val headerMapper: NoteHeaderMapper<NoteHeaderCacheModel>,
    private val contentMapper: NoteContentMapper<NoteContentCacheModel>,
    private val colorMapper: NoteColorMapper<NoteColorCacheModel>
) : SimpleNoteMapper<SimpleNoteCacheModel> {
    override fun map(
        header: NoteHeaderMapped,
        content: NoteContentMapped,
        color: NoteColorMapped
    ) = SimpleNoteCacheModel(
        header.map(headerMapper),
        content.map(contentMapper),
        color.map(colorMapper)
    )
}

class MapperToNoteHeaderCacheModel : NoteHeaderMapper<NoteHeaderCacheModel> {
    override fun map(text: String) = NoteHeaderCacheModel(text)
}

class MapperToNoteContentCacheModel : NoteContentMapper<NoteContentCacheModel> {
    override fun map(text: String) = NoteContentCacheModel(text)
}

class MapperToNoteColorCacheModel : NoteColorMapper<NoteColorCacheModel> {
    override fun map(mainColor: Int, buttonsColor: Int, selectedColor: Int) =
        NoteColorCacheModel(mainColor, buttonsColor, selectedColor)
}