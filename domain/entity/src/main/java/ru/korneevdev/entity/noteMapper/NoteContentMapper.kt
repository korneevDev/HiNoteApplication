package ru.korneevdev.entity.noteMapper

interface NoteContentMapper<T> {

    fun map(text: String): T
}