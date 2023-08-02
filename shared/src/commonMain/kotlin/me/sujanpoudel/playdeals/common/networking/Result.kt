package me.sujanpoudel.playdeals.common.networking

sealed class Result<T> {
  class Success<T>(val data: T) : Result<T>()

  class Error<T>(val failure: Failure) : Result<T>()

  companion object {
    fun <T> failure(failure: Failure): Result<T> {
      return Error(failure = failure)
    }

    fun <T> success(data: T): Success<T> {
      return Success(data = data)
    }
  }
}
