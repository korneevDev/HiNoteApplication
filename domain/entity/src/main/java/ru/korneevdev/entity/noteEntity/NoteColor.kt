package ru.korneevdev.entity.noteEntity

import ru.korneevdev.entity.noteMapper.NoteColorMapper

interface NoteColorMapped {

    fun <T> map(mapper: NoteColorMapper<T>): T
}

data class NoteColor(
    private val mainColor: Int,
    private val buttonsColor: Int,
    private val selectedColor: Int
) : NoteColorMapped {
    override fun <T> map(mapper: NoteColorMapper<T>) =
        mapper.map(mainColor, buttonsColor, selectedColor)
}