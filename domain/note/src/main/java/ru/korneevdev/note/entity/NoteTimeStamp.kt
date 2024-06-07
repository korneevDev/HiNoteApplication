package ru.korneevdev.note.entity

data class NoteTimeStamp(
    private val createdTime: Long,
    private var lastEditedTime: Long
){
    fun updateNoteTime(newTimeStamp: NoteTimeStamp) =
        NoteTimeStamp(createdTime, newTimeStamp.lastEditedTime)
}