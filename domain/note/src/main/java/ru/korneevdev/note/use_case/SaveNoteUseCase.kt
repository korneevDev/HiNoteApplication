package ru.korneevdev.note.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevDev.core.ExceptionHandler
import ru.korneevDev.core.NoteException
import ru.korneevdev.entity.entity.ProcessingState
import ru.korneevdev.entity.entity.SimpleNote
import ru.korneevdev.entity.utils.TimeStampManager
import ru.korneevdev.note.SaveNoteRepository

interface SaveNoteUseCase {

    suspend fun saveNote(note: SimpleNote): Flow<ProcessingState>

    class Base(
        private val repository: SaveNoteRepository,
        private val exceptionHandler: ExceptionHandler<ProcessingState>,
        private val timeStampManager: TimeStampManager
    ): SaveNoteUseCase {
        override suspend fun saveNote(note: SimpleNote) =
            try{
                repository.saveNote(note, timeStampManager.getCurrentTimeStamp())
            } catch (e: NoteException){
                flow { emit(exceptionHandler.handleException(e)) }
            }
    }
}