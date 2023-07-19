package me.sujanpoudel.playdeals.common.domain.models

import kotlinx.serialization.Serializable

@Serializable
class Response<T>(val message: String, val data: T?)
