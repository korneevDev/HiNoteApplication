package ru.korneevdev.entity.test_utils

import ru.korneevdev.entity.noteEntity.Note
import ru.korneevdev.entity.noteEntity.NoteColor
import ru.korneevdev.entity.noteEntity.NoteContent
import ru.korneevdev.entity.noteEntity.NoteHeader
import ru.korneevdev.entity.noteEntity.NoteTimeStamp
import ru.korneevdev.entity.noteEntity.SimpleNote

object TestNoteBuilder {

    private var header = NoteHeader("Test header 0")
    private var content = NoteContent("Test content 0")
    private var color = NoteColor(0, 0, 0)

    private var id = 0
    private var timeStamp: NoteTimeStamp = NoteTimeStamp.FirstCreated(0)

    fun setTestFields(id: Int) = this.also {
        TestNoteBuilder.id = id
        header = NoteHeader("Test header $id")
        content = NoteContent("Test content $id")
        color = NoteColor(id, id, id)
        timeStamp = NoteTimeStamp.FirstCreated(id.toLong())
    }

    fun setUpdatedTimeStamp(createdTime: Long, lastEdited: Long) = this.also {
        timeStamp = NoteTimeStamp.Updated(createdTime, lastEdited)
    }

    fun buildSimpleNote() = SimpleNote(header, content, color)

    fun buildNote() = Note(id, timeStamp, buildSimpleNote())
}