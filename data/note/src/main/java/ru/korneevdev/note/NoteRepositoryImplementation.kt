package ru.korneevdev.note

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.korneevdev.core.CachedData
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.entity.SimpleNote

class NoteRepositoryImplementation(
    private val cacheDataSource: CacheDataSource,
    private val timeStampManager: TimeStampManager,
    private val dispatcherManager: DispatcherManager
) : GetNoteRepository, SaveNoteRepository, DeleteNoteRepository {
    private var cachedNote = CachedData<Note>()

    override suspend fun getNote(id: Int) =
        withContext(dispatcherManager.io()) {
            return@withContext cacheDataSource.getNote(id)
        }

    override suspend fun saveNote(note: SimpleNote) =
        withContext(dispatcherManager.io()) {
            return@withContext cacheDataSource.saveNote(
                note,
                timeStampManager.getCurrentTimeStamp()
            ).map {
                ProcessingState.Created(it)
            }
        }

    override suspend fun saveNote(newNote: SimpleNote, oldNoteId: Int) =
        withContext(dispatcherManager.io()) {
            val oldNote = getNote(oldNoteId).first()
            val currentTimeStamp = timeStampManager.getCurrentTimeLong()
            val updatedTimeStamp = oldNote.getUpdatedTimeStamp(currentTimeStamp)

            return@withContext cacheDataSource.saveNote(newNote, updatedTimeStamp).map {
                ProcessingState.Created(it)
            }
        }

    override suspend fun deleteNote(id: Int) {
        withContext(dispatcherManager.io()) {
            cacheDataSource.deleteNote(id).also {
                cachedNote.cacheData(it)
            }
        }
    }

    override suspend fun restoreDeletedNote() {
        withContext(dispatcherManager.io()) {
            cacheDataSource.saveNote(cachedNote.getData())
        }
    }
}