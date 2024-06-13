package ru.korneevdev.note

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.korneevDev.core.CachedData
import ru.korneevdev.entity.entity.Note

class NoteRepositoryImplementation(
    private val cacheDataSource: NoteCacheDataSource,
    private val dispatcherManager: DispatcherManager,
    private var cachedNote: CachedData<Note> = CachedData.Base()
) : GetNoteRepository, SaveNoteRepository, DeleteNoteRepository {

    override suspend fun getNote(id: Int) =
        withContext(dispatcherManager.io()) {
            return@withContext cacheDataSource.getNote(id)
        }

    override suspend fun saveNote(
        note: ru.korneevdev.entity.entity.SimpleNote,
        currentTimeStamp: ru.korneevdev.entity.entity.NoteTimeStamp
    ) =
        withContext(dispatcherManager.io()) {
            return@withContext cacheDataSource.saveNote(
                note,
                currentTimeStamp
            ).map {
                ru.korneevdev.entity.entity.ProcessingState.Created(it)
            }
        }

    override suspend fun saveNote(
        newNote: ru.korneevdev.entity.entity.SimpleNote,
        currentTime: Long,
        oldNoteId: Int
    ) =
        withContext(dispatcherManager.io()) {
            val oldNote = getNote(oldNoteId).first()
            val updatedTimeStamp = oldNote.getUpdatedTimeStamp(currentTime)

            return@withContext cacheDataSource.saveNote(newNote, updatedTimeStamp).map {
                ru.korneevdev.entity.entity.ProcessingState.Created(it)
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