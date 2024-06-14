package ru.korneevdev.entity.noteEntity

import ru.korneevdev.entity.noteMapper.SimpleNoteMapper

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