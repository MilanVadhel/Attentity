package com.smvs.gkm.domain.base

sealed class UiState<out T> {
    class Loading<out T> : UiState<T>()
    class Empty<out T> : UiState<T>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error<out T>(val throwable: Throwable) : UiState<T>()
}