package ru.korneevdev.entity.utils

import ru.korneevdev.entity.entity.NoteTimeStamp

interface TimeStampManager {

    fun getCurrentTimeLong(): Long

    fun getCurrentTimeStamp(): ru.korneevdev.entity.entity.NoteTimeStamp

    class Base : TimeStampManager {
        override fun getCurrentTimeLong() = System.currentTimeMillis()
        override fun getCurrentTimeStamp() =
            ru.korneevdev.entity.entity.NoteTimeStamp.FirstCreated(getCurrentTimeLong())
    }
}