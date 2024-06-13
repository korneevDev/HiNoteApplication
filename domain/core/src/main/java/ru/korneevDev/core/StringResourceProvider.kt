package ru.korneevDev.core

interface StringResourceProvider {

    fun provideString(stringId: Int): String
}