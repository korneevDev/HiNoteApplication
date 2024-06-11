package ru.korneevdev.note.entity

import ru.korneevdev.note.mapper.NoteContentMapper

interface NoteContentMapped{
    fun <T> map(mapper: NoteContentMapper<T>): T
}

data class NoteContent(
    private val text: String
) : NoteContentMapped {
    override fun <T> map(mapper: NoteContentMapper<T>) = mapper.map(text)
}