package ru.korneevdev.note.use_case

import kotlinx.coroutines.flow.Flow
import ru.korneevdev.note.GetNoteRepository
import ru.korneevdev.entity.entity.Note

interface GetNoteUseCase {

    suspend fun getNote(id: Int): Flow<ru.korneevdev.entity.entity.Note>

    class Base(
        private val repository: GetNoteRepository
    ): GetNoteUseCase {
        override suspend fun getNote(id: Int): Flow<ru.korneevdev.entity.entity.Note> = repository.getNote(id)
    }
}