package ru.korneevdev.note.entity

sealed interface NoteTimeStamp{

    fun setLastEditedTime(newTime: Long): NoteTimeStamp
    data class FirstCreated(
        private val createdTime: Long
    ) : NoteTimeStamp {
        override fun setLastEditedTime(newTime: Long) = Updated(createdTime, newTime)
    }

    data class Updated(
        private val createdTime: Long,
        private var lastEditedTime: Long
    ) : NoteTimeStamp {
        override fun setLastEditedTime(newTime: Long) = Updated(createdTime, newTime)
    }
}
