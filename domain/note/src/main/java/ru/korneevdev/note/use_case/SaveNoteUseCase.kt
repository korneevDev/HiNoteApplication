package ru.korneevdev.note.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.korneevDev.core.ExceptionHandler
import ru.korneevDev.core.NoteException
import ru.korneevdev.note.SaveNoteRepository
import ru.korneevdev.entity.entity.ProcessingState
import ru.korneevdev.entity.utils.TimeStampManager

interface SaveNoteUseCase {

    suspend fun saveNote(note: ru.korneevdev.entity.entity.SimpleNote): Flow<ru.korneevdev.entity.entity.ProcessingState>

    class Base(
        private val repository: SaveNoteRepository,
        private val exceptionHandler: ru.korneevDev.core.ExceptionHandler<ProcessingState>,
        private val timeStampManager: TimeStampManager
    ): SaveNoteUseCase {
        override suspend fun saveNote(note: ru.korneevdev.entity.entity.SimpleNote) =
            try{
                repository.saveNote(note, timeStampManager.getCurrentTimeStamp())
            } catch (e: ru.korneevDev.core.NoteException){
                flow { emit(exceptionHandler.handleException(e)) }
            }
    }
}