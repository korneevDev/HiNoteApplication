package ru.korneevdev.note

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.korneevdev.core.CachedData
import ru.korneevdev.note.entity.Note
import ru.korneevdev.note.entity.NoteTimeStamp
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.entity.SimpleNote
import ru.korneevdev.note.utils.DispatcherManager

class NoteRepositoryImplementation(
    private val cacheDataSource: NoteCacheDataSource,
    private val dispatcherManager: DispatcherManager
) : GetNoteRepository, SaveNoteRepository, DeleteNoteRepository {
    private var cachedNote = CachedData<Note>()

    override suspend fun getNote(id: Int) =
        withContext(dispatcherManager.io()) {
            return@withContext cacheDataSource.getNote(id)
        }

    override suspend fun saveNote(note: SimpleNote, currentTimeStamp: NoteTimeStamp) =
        withContext(dispatcherManager.io()) {
            return@withContext cacheDataSource.saveNote(
                note,
                currentTimeStamp
            ).map {
                ProcessingState.Created(it)
            }
        }

    override suspend fun saveNote(newNote: SimpleNote, currentTime: Long, oldNoteId: Int) =
        withContext(dispatcherManager.io()) {
            val oldNote = getNote(oldNoteId).first()
            val updatedTimeStamp = oldNote.getUpdatedTimeStamp(currentTime)

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