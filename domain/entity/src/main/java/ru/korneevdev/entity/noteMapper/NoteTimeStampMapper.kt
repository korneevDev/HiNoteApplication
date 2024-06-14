package ru.korneevdev.entity.noteMapper

interface NoteTimeStampMapper<T> {

    fun mapFirstCreatedTimeStamp(createTime: Long): T

    fun mapUpdatedTimeStamp(createTime: Long, lastEditedTime: Long): T
}