package ru.korneevdev.note.entity

sealed interface NoteTimeStamp{
    data class FirstCreated(
        private val createdTime: Long
    ) : NoteTimeStamp

    data class Updated(
        private val createdTime: Long,
        private var lastEditedTime: Long
    ) : NoteTimeStamp
}
