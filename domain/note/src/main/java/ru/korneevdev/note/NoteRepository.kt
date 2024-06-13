package ru.korneevdev.note

import kotlinx.coroutines.flow.Flow
import ru.korneevdev.entity.entity.Note
import ru.korneevdev.entity.entity.NoteTimeStamp
import ru.korneevdev.entity.entity.ProcessingState
import ru.korneevdev.entity.entity.SimpleNote

interface GetNoteRepository {

    suspend fun getNote(id: Int): Flow<Note>
}

interface SaveNoteRepository {

    suspend fun saveNote(note: SimpleNote, currentTimeStamp: NoteTimeStamp): Flow<ProcessingState>

    suspend fun saveNote(
        newNote: SimpleNote, currentTime: Long, oldNoteId: Int
    ): Flow<ProcessingState>
}

interface DeleteNoteRepository {

    suspend fun deleteNote(id: Int)

    suspend fun restoreDeletedNote()
}
