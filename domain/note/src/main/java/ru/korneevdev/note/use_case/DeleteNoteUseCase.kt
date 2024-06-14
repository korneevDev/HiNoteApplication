package ru.korneevdev.note.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevdev.entity.noteEntity.ProcessingState
import ru.korneevdev.note.DeleteNoteRepository

interface DeleteNoteUseCase {

    suspend fun deleteNote(id: Int): Flow<ProcessingState>

    suspend fun restoreNote()

    class Base(
        private val repository: DeleteNoteRepository
    ) : DeleteNoteUseCase {
        override suspend fun deleteNote(id: Int): Flow<ProcessingState> {
            repository.deleteNote(id)
            return flow { emit(ProcessingState.Deleted(id)) }
        }

        override suspend fun restoreNote() {
            repository.restoreDeletedNote()
        }
    }
}