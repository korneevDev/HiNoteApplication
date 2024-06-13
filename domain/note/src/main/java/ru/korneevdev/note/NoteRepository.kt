package ru.korneevdev.note

import kotlinx.coroutines.flow.Flow
import ru.korneevdev.entity.entity.Note
import ru.korneevdev.entity.entity.NoteTimeStamp
import ru.korneevdev.entity.entity.ProcessingState
import ru.korneevdev.entity.entity.SimpleNote

interface GetNoteRepository {

    suspend fun getNote(id: Int): Flow<ru.korneevdev.entity.entity.Note>
}

interface SaveNoteRepository {

    suspend fun saveNote(note: ru.korneevdev.entity.entity.SimpleNote, currentTimeStamp: ru.korneevdev.entity.entity.NoteTimeStamp): Flow<ru.korneevdev.entity.entity.ProcessingState>

    suspend fun saveNote(
        newNote: ru.korneevdev.entity.entity.SimpleNote,
        currentTime: Long,
        oldNoteId: Int
    ): Flow<ru.korneevdev.entity.entity.ProcessingState>
}

interface DeleteNoteRepository {

    suspend fun deleteNote(id: Int)

    suspend fun restoreDeletedNote()
}
