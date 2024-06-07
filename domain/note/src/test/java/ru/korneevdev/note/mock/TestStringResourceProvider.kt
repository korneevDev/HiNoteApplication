package ru.korneevdev.note.mock

import ru.korneevdev.core.StringResourceProvider
import ru.korneevdev.note.R

class TestStringResourceProvider : StringResourceProvider {
    override fun provideString(stringId: Int): String =
        when(stringId){
            R.string.sameUpdatedNotesException -> TestConstants.errorNoChanges
            R.string.outOfMemoryNotesException -> TestConstants.errorOutOfMemory
            else -> TestConstants.unexpectedError
        }


}

