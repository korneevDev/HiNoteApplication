package ru.korneevdev.note

import ru.korneevdev.entity.entity.Note
import ru.korneevdev.entity.entity.NoteColor
import ru.korneevdev.entity.entity.NoteColorMapped
import ru.korneevdev.entity.entity.NoteContent
import ru.korneevdev.entity.entity.NoteContentMapped
import ru.korneevdev.entity.entity.NoteHeader
import ru.korneevdev.entity.entity.NoteHeaderMapped
import ru.korneevdev.entity.entity.NoteTimeStamp
import ru.korneevdev.entity.entity.NoteTimeStampMapped
import ru.korneevdev.entity.entity.SimpleNote
import ru.korneevdev.entity.entity.SimpleNoteMapped
import ru.korneevdev.entity.mapper.NoteColorMapper
import ru.korneevdev.entity.mapper.NoteContentMapper
import ru.korneevdev.entity.mapper.NoteHeaderMapper
import ru.korneevdev.entity.mapper.NoteMapper
import ru.korneevdev.entity.mapper.NoteTimeStampMapper
import ru.korneevdev.entity.mapper.SimpleNoteMapper

class MapperToNoteModel(
    private val timeStampMapper: MapperToTimeStampModel,
    private val simpleNoteMapper: MapperToSimpleNoteModel
) : NoteMapper<Note> {

    override fun map(id: Int, timeStamp: NoteTimeStampMapped, simpleNote: SimpleNoteMapped) =
        Note(id, timeStamp.map(timeStampMapper), simpleNote.map(simpleNoteMapper))
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

    override fun map(header: NoteHeaderMapped, content: NoteContentMapped, color: NoteColorMapped) =
        SimpleNote(header.map(headerMapper), content.map(contentMapper), color.map(colorMapper))
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