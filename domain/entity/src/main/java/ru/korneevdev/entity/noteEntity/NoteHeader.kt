package ru.korneevdev.entity.noteEntity

import ru.korneevdev.entity.noteMapper.NoteHeaderMapper

interface NoteHeaderMapped{
    fun <T> map(mapper: NoteHeaderMapper<T>): T
}

data class NoteHeader(
    private val text: String
): NoteHeaderMapped {
    override fun <T> map(mapper: NoteHeaderMapper<T>) = mapper.map(text)
}
