package ru.korneevdev.core

interface ExceptionMapper<T> {

    fun mapException(text: String, imageId: Int): T
}