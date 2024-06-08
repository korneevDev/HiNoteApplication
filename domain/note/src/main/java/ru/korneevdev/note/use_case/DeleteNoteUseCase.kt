package ru.korneevdev.note.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevdev.note.DeleteNoteRepository
import ru.korneevdev.note.entity.ProcessingState

interface DeleteNoteUseCase {

    suspend fun deleteNote(id: Int): Flow<ProcessingState>

    class Base(
        private val repository: DeleteNoteRepository
    ) : DeleteNoteUseCase {
        override suspend fun deleteNote(id: Int): Flow<ProcessingState> {
            repository.deleteNote(id)
            return flow { emit(ProcessingState.Deleted(id)) }
        }
    }
}