package ru.korneevdev.note

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

interface DispatcherManager {

    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher

    class Base : DispatcherManager {
        override fun io() = Dispatchers.IO

        override fun main() = Dispatchers.Main
    }
}