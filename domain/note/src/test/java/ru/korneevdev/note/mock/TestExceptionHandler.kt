package ru.korneevdev.note.mock

import ru.korneevDev.core.ExceptionHandler
import ru.korneevdev.entity.entity.ProcessingState
import ru.korneevdev.note.exception.OutOfMemoryException
import ru.korneevdev.note.exception.SameUpdatedNotesException
import java.lang.Exception

class TestExceptionHandler : ru.korneevDev.core.ExceptionHandler<ProcessingState> {

    override fun handleException(exception: Exception) =
        when (exception) {
            is OutOfMemoryException -> ru.korneevdev.entity.entity.ProcessingState.Error(
                exception.message!!,
                0
            )

            is SameUpdatedNotesException -> ru.korneevdev.entity.entity.ProcessingState.Error(
                exception.message!!,
                1
            )

            else -> ru.korneevdev.entity.entity.ProcessingState.Error(
                exception.message ?: "Unexpected message",
                2
            )
        }
}