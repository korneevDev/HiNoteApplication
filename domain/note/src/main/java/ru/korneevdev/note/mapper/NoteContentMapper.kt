package ru.korneevdev.note.mapper

interface NoteContentMapper<T> {

    fun map(text: String): T
}