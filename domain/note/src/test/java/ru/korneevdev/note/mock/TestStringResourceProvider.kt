package ru.korneevdev.note.mock

import ru.korneevDev.core.StringResourceProvider
import ru.korneevdev.note.R

class TestStringResourceProvider : StringResourceProvider {
    override fun provideString(stringId: Int): String =
        when(stringId){
            R.string.sameUpdatedNotesException -> TestConstants.ERROR_NO_CHANGES_TEXT
            R.string.outOfMemoryNotesException -> TestConstants.ERROR_OUT_OF_MEMORY_TEXT
            else -> TestConstants.UNEXPECTED_ERROR_TEXT
        }


}

