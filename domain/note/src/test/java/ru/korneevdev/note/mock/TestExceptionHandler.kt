package ru.korneevdev.note.mock

import ru.korneevDev.core.ExceptionHandler
import ru.korneevdev.entity.entity.ProcessingState
import ru.korneevdev.note.exception.OutOfMemoryException
import ru.korneevdev.note.exception.SameUpdatedNotesException

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