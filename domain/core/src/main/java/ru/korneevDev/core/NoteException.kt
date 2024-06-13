package ru.korneevDev.core

abstract class NoteException(private val text: String) : Exception(text){

    abstract val imageRes: Int

    fun <E> map(mapper: ExceptionMapper<E>) = mapper.mapException(text, imageRes)
}