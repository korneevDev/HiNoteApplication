package ru.korneevdev.note.exception

import ru.korneevdev.core.NoteException
import ru.korneevdev.core.StringResourceProvider
import ru.korneevdev.note.R

class SameUpdatedNotesException(
    stringResourceProvider: StringResourceProvider
) : NoteException(
    stringResourceProvider.provideString(R.string.sameUpdatedNotesException)
) {
    override val imageRes: Int
        get() = R.drawable.same_notes_exception
}