package ru.korneevdev.note.mapper

import ru.korneevdev.note.entity.NoteColorMapped
import ru.korneevdev.note.entity.NoteContentMapped
import ru.korneevdev.note.entity.NoteHeaderMapped
import ru.korneevdev.note.entity.NoteTimeStampMapped
import ru.korneevdev.note.entity.SimpleNoteMapped


interface SimpleNoteMapper<T> {

    fun map(header: NoteHeaderMapped, content: NoteContentMapped, color: NoteColorMapped): T
}

interface NoteMapper<T> {

    fun map(id: Int, timeStamp: NoteTimeStampMapped, simpleNote: SimpleNoteMapped): T
}