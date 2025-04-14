package com.example.data

sealed class NetworkResponse<out D> {
    data class Success<D>(val data:D) : NetworkResponse<D>()
    data class Error(val error:String):NetworkResponse<Nothing>()
}