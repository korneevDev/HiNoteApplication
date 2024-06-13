package ru.korneevdev.entity.entity

import ru.korneevdev.entity.mapper.NoteTimeStampMapper

interface NoteTimeStampMapped {

    fun <T> map(mapper: NoteTimeStampMapper<T>): T
}

sealed interface NoteTimeStamp : NoteTimeStampMapped {

    fun setLastEditedTime(newTime: Long): NoteTimeStamp
    data class FirstCreated(
        private val createdTime: Long
    ) : NoteTimeStamp {
        override fun setLastEditedTime(newTime: Long) = Updated(createdTime, newTime)
        override fun <T> map(mapper: NoteTimeStampMapper<T>) =
            mapper.mapFirstCreatedTimeStamp(createdTime)
    }

    data class Updated(
        private val createdTime: Long,
        private var lastEditedTime: Long
    ) : NoteTimeStamp {
        override fun setLastEditedTime(newTime: Long) = Updated(createdTime, newTime)
        override fun <T> map(mapper: NoteTimeStampMapper<T>) =
            mapper.mapUpdatedTimeStamp(createdTime, lastEditedTime)
    }
}
