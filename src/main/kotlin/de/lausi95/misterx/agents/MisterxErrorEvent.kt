package de.lausi95.misterx.agents

import java.time.LocalDateTime

data class MisterxErrorEvent(
  val code: String,
  val context: String,
  val message: String,
  val time: LocalDateTime,
)