package ru.korneevdev.entity.noteMapper

import ru.korneevdev.entity.noteEntity.NoteColorMapped
import ru.korneevdev.entity.noteEntity.NoteContentMapped
import ru.korneevdev.entity.noteEntity.NoteHeaderMapped
import ru.korneevdev.entity.noteEntity.NoteTimeStampMapped
import ru.korneevdev.entity.noteEntity.SimpleNoteMapped


interface SimpleNoteMapper<T> {

    fun map(header: NoteHeaderMapped, content: NoteContentMapped, color: NoteColorMapped): T
}

interface NoteMapper<T> {

    fun map(id: Int, timeStamp: NoteTimeStampMapped, simpleNote: SimpleNoteMapped): T
}