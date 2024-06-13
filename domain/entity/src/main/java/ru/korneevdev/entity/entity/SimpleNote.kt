package ru.korneevdev.entity.entity

import ru.korneevdev.entity.mapper.SimpleNoteMapper

interface SimpleNoteMapped{
    fun <T> map(mapper: SimpleNoteMapper<T>) : T
}

data class SimpleNote(
    private val header: NoteHeader,
    private val content: NoteContent,
    private val color: NoteColor
) : SimpleNoteMapped {
    override fun <T> map(mapper: SimpleNoteMapper<T>) = mapper.map(header, content, color)
}