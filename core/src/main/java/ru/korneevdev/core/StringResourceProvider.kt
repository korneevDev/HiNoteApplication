package ru.korneevdev.core

interface StringResourceProvider {

    fun provideString(stringId: Int): String
}