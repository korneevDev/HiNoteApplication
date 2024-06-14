package ru.korneevdev.entity.noteMapper

interface NoteHeaderMapper<T> {

    fun map(text: String): T
}