package ru.korneevdev.note.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import ru.korneevdev.core.ExceptionHandler
import ru.korneevdev.core.NoteException
import ru.korneevdev.core.StringResourceProvider
import ru.korneevdev.note.GetNoteRepository
import ru.korneevdev.note.SaveNoteRepository
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.entity.SimpleNote
import ru.korneevdev.note.exception.SameUpdatedNotesException

interface UpdateNoteUseCase {

    suspend fun updateNote(id: Int, newNote: SimpleNote): Flow<ProcessingState>

    class Base(
        private val saveRepository: SaveNoteRepository,
        private val getRepository: GetNoteRepository,
        private val exceptionHandler: ExceptionHandler<ProcessingState>,
        private val stringResourceProvider: StringResourceProvider
    ) : UpdateNoteUseCase {
        override suspend fun updateNote(id: Int, newNote: SimpleNote): Flow<ProcessingState> =
            flow {
                try {
                    val oldNote = getRepository.getNote(id).first()
                    if (!oldNote.equals(newNote)) {
                        emit(saveRepository.saveNote(newNote, id).first())
                    } else {
                        throw SameUpdatedNotesException(stringResourceProvider)
                    }
                } catch (e: NoteException) {
                    emit(exceptionHandler.handleException(e))
                }
            }
    }

}