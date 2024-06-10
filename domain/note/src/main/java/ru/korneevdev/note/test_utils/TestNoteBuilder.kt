package ru.korneevdev.note.test_utils

import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteColor
import ru.korneevdev.note.entity.NoteContent
import ru.korneevdev.note.entity.NoteHeader
import ru.korneevdev.note.entity.NoteTimeStamp
import ru.korneevdev.note.entity.SimpleNote

class TestNoteBuilder {

    private var header = NoteHeader("Test header 0")
    private var content = NoteContent("Test content 0")
    private var color = NoteColor(0, 0, 0)

    private var id = 0
    private var timeStamp: NoteTimeStamp = NoteTimeStamp.FirstCreated(0)

    fun setTestFields(id: Int) = this.also {
        it.id = id
        it.header = NoteHeader("Test header $id")
        it.content = NoteContent("Test content $id")
        it.color = NoteColor(id, id, id)
        it.timeStamp = NoteTimeStamp.FirstCreated(id.toLong())
    }

    fun setUpdatedTimeStamp(createdTime: Long, lastEdited: Long) = this.also {
        it.timeStamp = NoteTimeStamp.Updated(createdTime, lastEdited)
    }

    fun buildSimpleNote() = SimpleNote(header, content, color)

    fun buildNote() = Note(id, timeStamp, buildSimpleNote())
}