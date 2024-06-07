package ru.korneevdev.note.entity

data class Note(
    private val id: NoteId,
    private val header: NoteHeader,
    private val content: NoteContent,
    private val timeStamp: NoteTimeStamp,
    private val color: NoteColor
) {

    fun update(newNote: Note) =
        Note(
            newNote.id,
            newNote.header,
            newNote.content,
            timeStamp.updateNoteTime(newNote.timeStamp),
            newNote.color
        )

    fun checkIdSame(id: NoteId) = this.id == id

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (header != other.header) return false
        if (content != other.content) return false
        if (color != other.color) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + header.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + timeStamp.hashCode()
        result = 31 * result + color.hashCode()
        return result
    }

}

