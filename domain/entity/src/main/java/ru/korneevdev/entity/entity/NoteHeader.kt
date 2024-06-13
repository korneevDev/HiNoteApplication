package ru.korneevdev.entity.entity

import ru.korneevdev.entity.mapper.NoteHeaderMapper

interface NoteHeaderMapped{
    fun <T> map(mapper: NoteHeaderMapper<T>): T
}

data class NoteHeader(
    private val text: String
): NoteHeaderMapped {
    override fun <T> map(mapper: NoteHeaderMapper<T>) = mapper.map(text)
}
