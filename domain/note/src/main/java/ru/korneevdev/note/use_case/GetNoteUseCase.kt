package ru.korneevdev.note.use_case

import kotlinx.coroutines.flow.Flow
import ru.korneevdev.note.GetNoteRepository
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteId

interface GetNoteUseCase {

    suspend fun getNote(id: NoteId): Flow<Note>

    class Base(
        private val repository: GetNoteRepository
    ): GetNoteUseCase {
        override suspend fun getNote(id: NoteId): Flow<Note> = repository.getNote(id)
    }
}