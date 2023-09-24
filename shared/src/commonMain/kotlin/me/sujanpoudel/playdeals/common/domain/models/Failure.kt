package me.sujanpoudel.playdeals.common.domain.models

import io.ktor.client.network.sockets.ConnectTimeoutException
import me.sujanpoudel.playdeals.common.domain.models.Failure.FeatureFailure

/**
 * Base Class for handling errors/failures/exceptions.
 * Every feature specific failure should extend [FeatureFailure] class.
 */
sealed class Failure(val message: String) {
  object NetworkConnection : Failure("Couldn't reach server")
  object UnknownError : Failure("Something went wrong")

  /** * Extend this class for feature specific failures.*/
  sealed class FeatureFailure(message: String) : Failure(message) {
    class MessagedError(message: String) : FeatureFailure(message)
  }

  override fun toString() = message
}

fun Exception.resolveToFailure(): Failure {
  printStackTrace()

  return when (this) {
    is ConnectTimeoutException -> Failure.NetworkConnection
    else -> Failure.UnknownError
  }
}
