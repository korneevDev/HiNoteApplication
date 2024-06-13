package ru.korneevdev.entity.mapper

import ru.korneevdev.entity.entity.NoteColorMapped
import ru.korneevdev.entity.entity.NoteContentMapped
import ru.korneevdev.entity.entity.NoteHeaderMapped
import ru.korneevdev.entity.entity.NoteTimeStampMapped
import ru.korneevdev.entity.entity.SimpleNoteMapped


interface SimpleNoteMapper<T> {

    fun map(header: ru.korneevdev.entity.entity.NoteHeaderMapped, content: ru.korneevdev.entity.entity.NoteContentMapped, color: ru.korneevdev.entity.entity.NoteColorMapped): T
}

interface NoteMapper<T> {

    fun map(id: Int, timeStamp: ru.korneevdev.entity.entity.NoteTimeStampMapped, simpleNote: ru.korneevdev.entity.entity.SimpleNoteMapped): T
}