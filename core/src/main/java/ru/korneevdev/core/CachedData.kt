package ru.korneevdev.core

class CachedData<T> {
    private var data: T? = null

    fun cacheData(data: T){
        this.data = data
    }

    fun getData() = data!!
}