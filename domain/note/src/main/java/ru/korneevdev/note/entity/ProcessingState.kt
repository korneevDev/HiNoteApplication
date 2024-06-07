package ru.korneevdev.note.entity

sealed interface ProcessingState {

    data class Success(
        private val id: NoteId
    ) : ProcessingState

    data class Error(
        private val text: String,
        private val imageRes: Int
    ): ProcessingState
}