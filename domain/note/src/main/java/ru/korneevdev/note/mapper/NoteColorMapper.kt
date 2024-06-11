package ru.korneevdev.note.mapper

interface NoteColorMapper<T> {

    fun map(mainColor: Int, buttonsColor: Int, selectedColor: Int): T
}