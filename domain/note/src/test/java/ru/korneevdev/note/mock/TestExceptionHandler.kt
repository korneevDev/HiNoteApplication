package ru.korneevdev.note.mock

import ru.korneevdev.core.ExceptionHandler
import ru.korneevdev.note.entity.ProcessingState
import ru.korneevdev.note.exception.OutOfMemoryException
import ru.korneevdev.note.exception.SameUpdatedNotesException
import java.lang.Exception

class TestExceptionHandler : ExceptionHandler<ProcessingState> {

    override fun handleException(exception: Exception) =
        when (exception) {
            is OutOfMemoryException -> ProcessingState.Error(
                exception.message!!,
                0
            )

            is SameUpdatedNotesException -> ProcessingState.Error(
                exception.message!!,
                1
            )

            else -> ProcessingState.Error(
                exception.message ?: "Unexpected message",
                2
            )
        }
}