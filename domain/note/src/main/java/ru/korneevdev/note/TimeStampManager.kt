package ru.korneevdev.note

import ru.korneevdev.note.entity.NoteTimeStamp

interface TimeStampManager {

    fun getCurrentTimeLong(): Long

    fun getCurrentTimeStamp(): NoteTimeStamp

    class Base : TimeStampManager {
        override fun getCurrentTimeLong() = System.currentTimeMillis()
        override fun getCurrentTimeStamp() =
            NoteTimeStamp.FirstCreated(getCurrentTimeLong())
    }
}