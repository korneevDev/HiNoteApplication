package ru.korneevdev.room.room.mapperImplementation

class MapperToNoteModel(
    private val timeStampMapper: MapperToTimeStampModel,
    private val simpleNoteMapper: MapperToSimpleNoteModel
) : ru.korneevdev.entity.mapper.NoteMapper<ru.korneevdev.entity.entity.Note> {
    override fun map(
        id: Int,
        timeStamp: ru.korneevdev.entity.entity.NoteTimeStampMapped,
        simpleNote: ru.korneevdev.entity.entity.SimpleNoteMapped
    ) = ru.korneevdev.entity.entity.Note(
        id,
        timeStamp.map(timeStampMapper),
        simpleNote.map(simpleNoteMapper)
    )
}

class MapperToTimeStampModel :
    ru.korneevdev.entity.mapper.NoteTimeStampMapper<ru.korneevdev.entity.entity.NoteTimeStamp> {
    override fun mapFirstCreatedTimeStamp(createTime: Long) =
        ru.korneevdev.entity.entity.NoteTimeStamp.FirstCreated(createTime)

    override fun mapUpdatedTimeStamp(createTime: Long, lastEditedTime: Long) =
        ru.korneevdev.entity.entity.NoteTimeStamp.Updated(createTime, lastEditedTime)
}

class MapperToSimpleNoteModel(
    private val headerMapper: ru.korneevdev.entity.mapper.NoteHeaderMapper<ru.korneevdev.entity.entity.NoteHeader>,
    private val contentMapper: ru.korneevdev.entity.mapper.NoteContentMapper<ru.korneevdev.entity.entity.NoteContent>,
    private val colorMapper: ru.korneevdev.entity.mapper.NoteColorMapper<ru.korneevdev.entity.entity.NoteColor>
) : ru.korneevdev.entity.mapper.SimpleNoteMapper<ru.korneevdev.entity.entity.SimpleNote> {
    override fun map(
        header: ru.korneevdev.entity.entity.NoteHeaderMapped,
        content: ru.korneevdev.entity.entity.NoteContentMapped,
        color: ru.korneevdev.entity.entity.NoteColorMapped
    ) = ru.korneevdev.entity.entity.SimpleNote(
        header.map(headerMapper),
        content.map(contentMapper),
        color.map(colorMapper)
    )
}

class MapperToNoteHeader :
    ru.korneevdev.entity.mapper.NoteHeaderMapper<ru.korneevdev.entity.entity.NoteHeader> {
    override fun map(text: String) = ru.korneevdev.entity.entity.NoteHeader(text)
}

class MapperToNoteContent :
    ru.korneevdev.entity.mapper.NoteContentMapper<ru.korneevdev.entity.entity.NoteContent> {
    override fun map(text: String) = ru.korneevdev.entity.entity.NoteContent(text)
}

class MapperToNoteColor :
    ru.korneevdev.entity.mapper.NoteColorMapper<ru.korneevdev.entity.entity.NoteColor> {
    override fun map(mainColor: Int, buttonsColor: Int, selectedColor: Int) =
        ru.korneevdev.entity.entity.NoteColor(mainColor, buttonsColor, selectedColor)
}