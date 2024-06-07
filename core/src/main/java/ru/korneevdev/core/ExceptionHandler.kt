package ru.korneevdev.core

import java.lang.Exception

interface ExceptionHandler<E> {

    fun handleException(exception: Exception): E
}