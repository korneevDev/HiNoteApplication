package ru.korneevdev.note.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevdev.core.ExceptionHandler
import ru.korneevdev.core.NoteException
import ru.korneevdev.note.SaveNoteRepository
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.ProcessingState

interface SaveNoteUseCase {

    suspend fun saveNote(note: Note): Flow<ProcessingState>

    class Base(
        private val repository: SaveNoteRepository,
        private val exceptionHandler: ExceptionHandler<ProcessingState>
    ): SaveNoteUseCase {
        override suspend fun saveNote(note: Note) =
            try{
                repository.saveNote(note)
            } catch (e: NoteException){
                flow { emit(exceptionHandler.handleException(e)) }
            }
    }
}