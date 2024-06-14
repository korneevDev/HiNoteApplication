package ru.korneevdev.entity.noteEntity

import ru.korneevdev.entity.noteMapper.NoteMapper

interface NoteMapped {

    fun <T> map(mapper: NoteMapper<T>) : T
}

data class Note(
    private val id: Int,
    private val timeStamp: NoteTimeStamp,
    private val simpleNote: SimpleNote
) : NoteMapped {

    fun getUpdatedTimeStamp(newTime: Long) = timeStamp.setLastEditedTime(newTime)

    override fun <T> map(mapper: NoteMapper<T>): T = mapper.map(id, timeStamp, simpleNote)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass && simpleNote.javaClass != other?.javaClass)
            return false

        val otherSimpleNote = if (other is Note) other.simpleNote else other

        return simpleNote == otherSimpleNote
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + timeStamp.hashCode()
        result = 31 * result + simpleNote.hashCode()
        return result
    }
}

