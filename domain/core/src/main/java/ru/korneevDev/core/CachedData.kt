package ru.korneevDev.core

interface CachedData<T> {

    fun cacheData(data: T)

    fun getData() : T

    class Base<T> : CachedData<T>{
        private var data: T? = null

        override fun cacheData(data: T){
            this.data = data
        }

        override fun getData() = data!!
    }
}