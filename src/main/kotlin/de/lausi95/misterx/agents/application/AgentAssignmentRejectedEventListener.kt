package de.lausi95.misterx.agents.application

import de.lausi95.misterx.ErrorEvent
import de.lausi95.misterx.agents.AgentAssignmentRejectedEvent
import de.lausi95.misterx.agents.AgentState
import de.lausi95.misterx.agents.AgentUpdatedEvent
import de.lausi95.misterx.agents.domain.AgentRepository
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AgentAssignmentRejectedEventListener(
  private val agentRepository: AgentRepository,
  private val applicationEventPublisher: ApplicationEventPublisher,
) {

  private val log = LoggerFactory.getLogger(AgentAssignmentRejectedEventListener::class.java)

  @ApplicationModuleListener
  fun onAgentAssignmentRejected(event: AgentAssignmentRejectedEvent) {
    val agent = agentRepository.findById(event.agentId)
    if (agent == null) {
      log.warn("Agent is null.")
      applicationEventPublisher.publishEvent(
        ErrorEvent(
          "agent-not-found",
          "onAgentAssignmentRejected",
          "Agent ${event.agentId} not found.",
          LocalDateTime.now()
        )
      )
      return
    }

    if (agent.state != AgentState.PENDING) {
      log.warn("Agent has wrong state.")
      applicationEventPublisher.publishEvent(
        ErrorEvent(
          "agent-not-pending",
          "onAgentAssignmentRejected",
          "Agent ${event.agentId} is not in status 'PENDING', actual state: ${agent.state}.",
          LocalDateTime.now()
        )
      )
      return
    }

    agent.state = AgentState.REJECTED

    agentRepository.save(agent)
    applicationEventPublisher.publishEvent(AgentUpdatedEvent(agent.id, "Agent Rejected. Reason: ${event.reason}."))
  }
}
