package com.example.domain.model

sealed class Result<out D> {
    object Loading: Result<Nothing>()
    data class Success<T>(val data : T): Result<T>()
    data class Error(val error: String): Result<Nothing>()
}