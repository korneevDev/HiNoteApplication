package ru.korneevDev.core

interface ExceptionHandler<E> {

    fun handleException(exception: Exception): E
}