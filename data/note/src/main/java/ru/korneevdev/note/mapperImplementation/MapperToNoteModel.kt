package ru.korneevdev.note.mapperImplementation

import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteColor
import ru.korneevdev.note.entity.NoteColorMapped
import ru.korneevdev.note.entity.NoteContent
import ru.korneevdev.note.entity.NoteContentMapped
import ru.korneevdev.note.entity.NoteHeader
import ru.korneevdev.note.entity.NoteHeaderMapped
import ru.korneevdev.note.entity.NoteTimeStamp
import ru.korneevdev.note.entity.NoteTimeStampMapped
import ru.korneevdev.note.entity.SimpleNote
import ru.korneevdev.note.entity.SimpleNoteMapped
import ru.korneevdev.note.mapper.NoteColorMapper
import ru.korneevdev.note.mapper.NoteContentMapper
import ru.korneevdev.note.mapper.NoteHeaderMapper
import ru.korneevdev.note.mapper.NoteMapper
import ru.korneevdev.note.mapper.NoteTimeStampMapper
import ru.korneevdev.note.mapper.SimpleNoteMapper

class MapperToNoteModel(
    private val timeStampMapper: MapperToTimeStampModel,
    private val simpleNoteMapper: MapperToSimpleNoteModel
) : NoteMapper<Note> {
    override fun map(
        id: Int,
        timeStamp: NoteTimeStampMapped,
        simpleNote: SimpleNoteMapped
    ) = Note(
        id,
        timeStamp.map(timeStampMapper),
        simpleNote.map(simpleNoteMapper)
    )
}

class MapperToTimeStampModel : NoteTimeStampMapper<NoteTimeStamp> {
    override fun mapFirstCreatedTimeStamp(createTime: Long) =
        NoteTimeStamp.FirstCreated(createTime)

    override fun mapUpdatedTimeStamp(createTime: Long, lastEditedTime: Long) =
        NoteTimeStamp.Updated(createTime, lastEditedTime)
}

class MapperToSimpleNoteModel(
    private val headerMapper: NoteHeaderMapper<NoteHeader>,
    private val contentMapper: NoteContentMapper<NoteContent>,
    private val colorMapper: NoteColorMapper<NoteColor>
) : SimpleNoteMapper<SimpleNote> {
    override fun map(
        header: NoteHeaderMapped,
        content: NoteContentMapped,
        color: NoteColorMapped
    ) = SimpleNote(
        header.map(headerMapper),
        content.map(contentMapper),
        color.map(colorMapper)
    )
}

class MapperToNoteHeader : NoteHeaderMapper<NoteHeader> {
    override fun map(text: String) = NoteHeader(text)
}

class MapperToNoteContent : NoteContentMapper<NoteContent> {
    override fun map(text: String) = NoteContent(text)
}

class MapperToNoteColor : NoteColorMapper<NoteColor> {
    override fun map(mainColor: Int, buttonsColor: Int, selectedColor: Int) =
        NoteColor(mainColor, buttonsColor, selectedColor)
}