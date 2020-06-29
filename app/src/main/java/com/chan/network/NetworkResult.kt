package com.chan.network

sealed class NetworkResult<out R> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Failure(val exception: Exception) : NetworkResult<Nothing>()
}