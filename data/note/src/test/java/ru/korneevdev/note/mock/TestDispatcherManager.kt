package ru.korneevdev.note.mock

import kotlinx.coroutines.CoroutineDispatcher
import ru.korneevdev.note.utils.DispatcherManager

class TestDispatcherManager(
    private val dispatcher: CoroutineDispatcher
) : DispatcherManager {
    override fun io() = dispatcher

    override fun main() = dispatcher
}