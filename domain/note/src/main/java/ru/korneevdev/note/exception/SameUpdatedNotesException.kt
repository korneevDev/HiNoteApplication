package ru.korneevdev.note.exception

import ru.korneevDev.core.NoteException
import ru.korneevDev.core.StringResourceProvider
import ru.korneevdev.note.R

class SameUpdatedNotesException(
    stringResourceProvider: StringResourceProvider
) : NoteException(
    stringResourceProvider.provideString(R.string.sameUpdatedNotesException)
) {
    override val imageRes: Int
        get() = R.drawable.same_notes_exception
}