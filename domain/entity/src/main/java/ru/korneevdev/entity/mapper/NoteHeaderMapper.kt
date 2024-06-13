package ru.korneevdev.entity.mapper

interface NoteHeaderMapper<T> {

    fun map(text: String): T
}