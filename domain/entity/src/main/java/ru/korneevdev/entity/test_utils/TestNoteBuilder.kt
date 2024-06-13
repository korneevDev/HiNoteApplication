package ru.korneevdev.entity.test_utils

import ru.korneevdev.entity.entity.Note
import ru.korneevdev.entity.entity.NoteColor
import ru.korneevdev.entity.entity.NoteContent
import ru.korneevdev.entity.entity.NoteHeader
import ru.korneevdev.entity.entity.NoteTimeStamp
import ru.korneevdev.entity.entity.SimpleNote

object TestNoteBuilder {

    private var header = ru.korneevdev.entity.entity.NoteHeader("Test header 0")
    private var content = ru.korneevdev.entity.entity.NoteContent("Test content 0")
    private var color = ru.korneevdev.entity.entity.NoteColor(0, 0, 0)

    private var id = 0
    private var timeStamp: ru.korneevdev.entity.entity.NoteTimeStamp = ru.korneevdev.entity.entity.NoteTimeStamp.FirstCreated(0)

    fun setTestFields(id: Int) = this.also {
        TestNoteBuilder.id = id
        header = ru.korneevdev.entity.entity.NoteHeader("Test header $id")
        content = ru.korneevdev.entity.entity.NoteContent("Test content $id")
        color = ru.korneevdev.entity.entity.NoteColor(id, id, id)
        timeStamp = ru.korneevdev.entity.entity.NoteTimeStamp.FirstCreated(id.toLong())
    }

    fun setUpdatedTimeStamp(createdTime: Long, lastEdited: Long) = this.also {
        timeStamp = ru.korneevdev.entity.entity.NoteTimeStamp.Updated(createdTime, lastEdited)
    }

    fun buildSimpleNote() = ru.korneevdev.entity.entity.SimpleNote(header, content, color)

    fun buildNote() = ru.korneevdev.entity.entity.Note(id, timeStamp, buildSimpleNote())
}