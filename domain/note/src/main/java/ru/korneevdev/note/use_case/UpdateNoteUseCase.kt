package ru.korneevdev.note.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.korneevDev.core.ExceptionHandler
import ru.korneevDev.core.NoteException
import ru.korneevDev.core.StringResourceProvider
import ru.korneevdev.note.GetNoteRepository
import ru.korneevdev.note.SaveNoteRepository
import ru.korneevdev.entity.entity.ProcessingState
import ru.korneevdev.note.exception.SameUpdatedNotesException
import ru.korneevdev.entity.utils.TimeStampManager

interface UpdateNoteUseCase {

    suspend fun updateNote(id: Int, newNote: ru.korneevdev.entity.entity.SimpleNote): Flow<ru.korneevdev.entity.entity.ProcessingState>

    class Base(
        private val saveRepository: SaveNoteRepository,
        private val getRepository: GetNoteRepository,
        private val exceptionHandler: ru.korneevDev.core.ExceptionHandler<ProcessingState>,
        private val stringResourceProvider: ru.korneevDev.core.StringResourceProvider,
        private val timeStampManager: TimeStampManager
    ) : UpdateNoteUseCase {
        override suspend fun updateNote(id: Int, newNote: ru.korneevdev.entity.entity.SimpleNote): Flow<ru.korneevdev.entity.entity.ProcessingState> =
            try {
                val oldNote = getRepository.getNote(id).first()
                if (!oldNote.equals(newNote)) {
                    saveRepository.saveNote(newNote, timeStampManager.getCurrentTimeLong(), id)
                } else {
                    throw SameUpdatedNotesException(stringResourceProvider)
                }
            } catch (e: ru.korneevDev.core.NoteException) {
                flow { emit(exceptionHandler.handleException(e)) }
            }
    }

}