package ru.korneevdev.note.entity

sealed interface ProcessingState {

    data class Created(
        private val id: Int
    ) : ProcessingState

    data class Deleted(
        private val id: Int
    ) : ProcessingState

    data class Error(
        private val text: String,
        private val imageRes: Int
    ): ProcessingState
}