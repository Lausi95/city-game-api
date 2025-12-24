package de.lausi95.misterx

import java.time.LocalDateTime

data class ErrorEvent(
  val code: String,
  val context: String,
  val message: String,
  val time: LocalDateTime,
)