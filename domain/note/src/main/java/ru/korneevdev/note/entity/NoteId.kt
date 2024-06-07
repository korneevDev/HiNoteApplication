package ru.korneevdev.note.entity

sealed interface NoteId{

    data object EmptyId: NoteId
    data class Id(
        private val id: Int
    ): NoteId
}