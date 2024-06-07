package ru.korneevdev.note

import kotlinx.coroutines.flow.Flow
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteId
import ru.korneevdev.note.entity.ProcessingState

interface GetNoteRepository {

    suspend fun getNote(id: NoteId): Flow<Note>
}

interface SaveNoteRepository {

    suspend fun saveNote(note: Note): Flow<ProcessingState>
}
