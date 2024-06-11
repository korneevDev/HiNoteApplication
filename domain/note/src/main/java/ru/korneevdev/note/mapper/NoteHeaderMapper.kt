package ru.korneevdev.note.mapper

interface NoteHeaderMapper<T> {

    fun map(text: String): T
}