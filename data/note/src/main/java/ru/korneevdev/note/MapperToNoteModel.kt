package ru.korneevdev.note

import ru.korneevdev.entity.noteEntity.Note
import ru.korneevdev.entity.noteEntity.NoteColor
import ru.korneevdev.entity.noteEntity.NoteColorMapped
import ru.korneevdev.entity.noteEntity.NoteContent
import ru.korneevdev.entity.noteEntity.NoteContentMapped
import ru.korneevdev.entity.noteEntity.NoteHeader
import ru.korneevdev.entity.noteEntity.NoteHeaderMapped
import ru.korneevdev.entity.noteEntity.NoteTimeStamp
import ru.korneevdev.entity.noteEntity.NoteTimeStampMapped
import ru.korneevdev.entity.noteEntity.SimpleNote
import ru.korneevdev.entity.noteEntity.SimpleNoteMapped
import ru.korneevdev.entity.noteMapper.NoteColorMapper
import ru.korneevdev.entity.noteMapper.NoteContentMapper
import ru.korneevdev.entity.noteMapper.NoteHeaderMapper
import ru.korneevdev.entity.noteMapper.NoteMapper
import ru.korneevdev.entity.noteMapper.NoteTimeStampMapper
import ru.korneevdev.entity.noteMapper.SimpleNoteMapper

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