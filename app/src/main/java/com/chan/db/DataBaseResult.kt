package com.chan.db

sealed class DataBaseResult<out R> {
    data class Success<out T>(val data: T) : DataBaseResult<T>()
    data class Failure(val exception: Exception) : DataBaseResult<Nothing>()
}