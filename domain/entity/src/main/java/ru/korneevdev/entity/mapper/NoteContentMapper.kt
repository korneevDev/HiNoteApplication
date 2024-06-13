package ru.korneevdev.entity.mapper

interface NoteContentMapper<T> {

    fun map(text: String): T
}