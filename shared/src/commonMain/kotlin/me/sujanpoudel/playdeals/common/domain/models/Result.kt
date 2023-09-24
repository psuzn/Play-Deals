package me.sujanpoudel.playdeals.common.domain.models

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

inline fun <From, To> Result<From>.map(mapper: (From) -> To): Result<To> {
  return when (this) {
    is Result.Error -> Result.failure(this.failure)
    is Result.Success -> Result.success(mapper(data))
  }
}

inline fun <T> Result<T>.skipData(): Result<Unit> = map { }

inline fun <T> Result<T>.onSuccess(block: (T) -> Unit): Result<T> {
  if (this is Result.Success) {
    block(this.data)
  }
  return this
}
