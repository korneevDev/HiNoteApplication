package ru.korneevdev.entity.mapper

import ru.korneevdev.entity.entity.NoteColorMapped
import ru.korneevdev.entity.entity.NoteContentMapped
import ru.korneevdev.entity.entity.NoteHeaderMapped
import ru.korneevdev.entity.entity.NoteTimeStampMapped
import ru.korneevdev.entity.entity.SimpleNoteMapped


interface SimpleNoteMapper<T> {

    fun map(header: NoteHeaderMapped, content: NoteContentMapped, color: NoteColorMapped): T
}

interface NoteMapper<T> {

    fun map(id: Int, timeStamp: NoteTimeStampMapped, simpleNote: SimpleNoteMapped): T
}