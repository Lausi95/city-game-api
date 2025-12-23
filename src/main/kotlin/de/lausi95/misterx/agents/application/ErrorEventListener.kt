package de.lausi95.misterx.agents.application

import de.lausi95.misterx.agents.MisterxErrorEvent
import org.slf4j.LoggerFactory
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class ErrorEventListener {

  private val log = LoggerFactory.getLogger("error")

  @ApplicationModuleListener
  fun onErrorEvent(event: MisterxErrorEvent) {
    log.warn("{}", event)
  }
}
