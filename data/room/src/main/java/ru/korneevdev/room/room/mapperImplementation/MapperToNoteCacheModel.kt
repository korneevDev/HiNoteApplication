package ru.korneevdev.room.room.mapperImplementation

import ru.korneevdev.entity.entity.NoteColorMapped
import ru.korneevdev.entity.entity.NoteContentMapped
import ru.korneevdev.entity.entity.NoteHeaderMapped
import ru.korneevdev.entity.entity.NoteTimeStampMapped
import ru.korneevdev.entity.entity.SimpleNoteMapped
import ru.korneevdev.entity.mapper.NoteColorMapper
import ru.korneevdev.entity.mapper.NoteContentMapper
import ru.korneevdev.entity.mapper.NoteHeaderMapper
import ru.korneevdev.entity.mapper.NoteMapper
import ru.korneevdev.entity.mapper.NoteTimeStampMapper
import ru.korneevdev.entity.mapper.SimpleNoteMapper
import ru.korneevdev.room.room.NoteCacheModel
import ru.korneevdev.room.room.NoteColorCacheModel
import ru.korneevdev.room.room.NoteContentCacheModel
import ru.korneevdev.room.room.NoteHeaderCacheModel
import ru.korneevdev.room.room.SimpleNoteCacheModel
import ru.korneevdev.room.room.TimeStampCacheModel

class MapperToNoteCacheModel(
    private val timeStampMapper: ru.korneevdev.entity.mapper.NoteTimeStampMapper<ru.korneevdev.room.room.TimeStampCacheModel>,
    private val simpleNoteMapper: ru.korneevdev.entity.mapper.SimpleNoteMapper<ru.korneevdev.room.room.SimpleNoteCacheModel>
) : ru.korneevdev.entity.mapper.NoteMapper<ru.korneevdev.room.room.NoteCacheModel>,
    ru.korneevdev.entity.mapper.SimpleNoteMapper<ru.korneevdev.room.room.SimpleNoteCacheModel>,
    ru.korneevdev.entity.mapper.NoteTimeStampMapper<ru.korneevdev.room.room.TimeStampCacheModel> {
    override fun map(
        id: Int,
        timeStamp: ru.korneevdev.entity.entity.NoteTimeStampMapped,
        simpleNote: ru.korneevdev.entity.entity.SimpleNoteMapped
    ) = ru.korneevdev.room.room.NoteCacheModel(
        simpleNote.map(simpleNoteMapper),
        timeStamp.map(timeStampMapper)
    ).also {
        it.id = id
    }

    override fun map(
        header: ru.korneevdev.entity.entity.NoteHeaderMapped,
        content: ru.korneevdev.entity.entity.NoteContentMapped,
        color: ru.korneevdev.entity.entity.NoteColorMapped
    ) = simpleNoteMapper.map(header, content, color)

    override fun mapFirstCreatedTimeStamp(createTime: Long) =
        timeStampMapper.mapFirstCreatedTimeStamp(createTime)

    override fun mapUpdatedTimeStamp(createTime: Long, lastEditedTime: Long) =
        timeStampMapper.mapUpdatedTimeStamp(createTime, lastEditedTime)
}

class MapperToTimeStampCacheModel :
    ru.korneevdev.entity.mapper.NoteTimeStampMapper<ru.korneevdev.room.room.TimeStampCacheModel> {
    override fun mapFirstCreatedTimeStamp(createTime: Long) =
        ru.korneevdev.room.room.TimeStampCacheModel(createTime, null)

    override fun mapUpdatedTimeStamp(createTime: Long, lastEditedTime: Long) =
        ru.korneevdev.room.room.TimeStampCacheModel(createTime, lastEditedTime)
}

class MapperToSimpleNoteCacheModel(
    private val headerMapper: ru.korneevdev.entity.mapper.NoteHeaderMapper<ru.korneevdev.room.room.NoteHeaderCacheModel>,
    private val contentMapper: ru.korneevdev.entity.mapper.NoteContentMapper<ru.korneevdev.room.room.NoteContentCacheModel>,
    private val colorMapper: ru.korneevdev.entity.mapper.NoteColorMapper<ru.korneevdev.room.room.NoteColorCacheModel>
) : ru.korneevdev.entity.mapper.SimpleNoteMapper<ru.korneevdev.room.room.SimpleNoteCacheModel> {
    override fun map(
        header: ru.korneevdev.entity.entity.NoteHeaderMapped,
        content: ru.korneevdev.entity.entity.NoteContentMapped,
        color: ru.korneevdev.entity.entity.NoteColorMapped
    ) = ru.korneevdev.room.room.SimpleNoteCacheModel(
        header.map(headerMapper),
        content.map(contentMapper),
        color.map(colorMapper)
    )
}

class MapperToNoteHeaderCacheModel :
    ru.korneevdev.entity.mapper.NoteHeaderMapper<ru.korneevdev.room.room.NoteHeaderCacheModel> {
    override fun map(text: String) = ru.korneevdev.room.room.NoteHeaderCacheModel(text)
}

class MapperToNoteContentCacheModel :
    ru.korneevdev.entity.mapper.NoteContentMapper<ru.korneevdev.room.room.NoteContentCacheModel> {
    override fun map(text: String) = ru.korneevdev.room.room.NoteContentCacheModel(text)
}

class MapperToNoteColorCacheModel :
    ru.korneevdev.entity.mapper.NoteColorMapper<ru.korneevdev.room.room.NoteColorCacheModel> {
    override fun map(mainColor: Int, buttonsColor: Int, selectedColor: Int) =
        ru.korneevdev.room.room.NoteColorCacheModel(mainColor, buttonsColor, selectedColor)
}