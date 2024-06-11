package ru.korneevdev.note.entity

import ru.korneevdev.note.mapper.SimpleNoteMapper

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