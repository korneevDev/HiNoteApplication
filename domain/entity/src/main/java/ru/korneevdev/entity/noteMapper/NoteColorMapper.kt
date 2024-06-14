package ru.korneevdev.entity.noteMapper

interface NoteColorMapper<T> {

    fun map(mainColor: Int, buttonsColor: Int, selectedColor: Int): T
}