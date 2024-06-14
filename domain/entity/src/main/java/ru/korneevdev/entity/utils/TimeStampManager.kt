package ru.korneevdev.entity.utils

import ru.korneevdev.entity.noteEntity.NoteTimeStamp


interface TimeStampManager {

    fun getCurrentTimeLong(): Long

    fun getCurrentTimeStamp(): NoteTimeStamp

    class Base : TimeStampManager {
        override fun getCurrentTimeLong() = System.currentTimeMillis()
        override fun getCurrentTimeStamp() =
            NoteTimeStamp.FirstCreated(getCurrentTimeLong())
    }
}