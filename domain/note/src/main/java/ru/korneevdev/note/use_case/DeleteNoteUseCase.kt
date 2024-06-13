package ru.korneevdev.note.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevdev.note.DeleteNoteRepository
import ru.korneevdev.entity.entity.ProcessingState

interface DeleteNoteUseCase {

    suspend fun deleteNote(id: Int): Flow<ru.korneevdev.entity.entity.ProcessingState>

    suspend fun restoreNote()

    class Base(
        private val repository: DeleteNoteRepository
    ) : DeleteNoteUseCase {
        override suspend fun deleteNote(id: Int): Flow<ru.korneevdev.entity.entity.ProcessingState> {
            repository.deleteNote(id)
            return flow { emit(ru.korneevdev.entity.entity.ProcessingState.Deleted(id)) }
        }

        override suspend fun restoreNote() {
            repository.restoreDeletedNote()
        }
    }
}