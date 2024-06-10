package ru.korneevdev.note

import kotlinx.coroutines.flow.Flow
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.entity.SimpleNote

interface GetNoteRepository {

    suspend fun getNote(id: Int): Flow<Note>
}

interface SaveNoteRepository {

    suspend fun saveNote(note: SimpleNote): Flow<ProcessingState>

    suspend fun saveNote(newNote: SimpleNote, oldNoteId: Int): Flow<ProcessingState>
}

interface DeleteNoteRepository {

    suspend fun deleteNote(id: Int)

    suspend fun restoreDeletedNote()
}
