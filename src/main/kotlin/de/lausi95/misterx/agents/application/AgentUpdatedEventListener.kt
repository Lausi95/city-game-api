package de.lausi95.misterx.agents.application

import de.lausi95.misterx.agents.AgentUpdatedEvent
import org.slf4j.LoggerFactory
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component

@Component
class AgentUpdatedEventListener {

  private val log = LoggerFactory.getLogger("agent-updated")

  @ApplicationModuleListener
  fun onAgentUpdatedEvent(event: AgentUpdatedEvent) {
    log.debug("{}", event)
  }
}