package de.lausi95.misterx.agents.application

import de.lausi95.misterx.ErrorEvent
import de.lausi95.misterx.agents.AgentAssignmentAcceptedEvent
import de.lausi95.misterx.agents.AgentState
import de.lausi95.misterx.agents.AgentUpdatedEvent
import de.lausi95.misterx.agents.domain.AgentRepository
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationEventPublisher
import org.springframework.modulith.events.ApplicationModuleListener
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AgentAssignmentAcceptedEventListener(
  private val agentRepository: AgentRepository,
  private val applicationEventPublisher: ApplicationEventPublisher,
) {

  private val log = LoggerFactory.getLogger(AgentAssignmentAcceptedEventListener::class.java)

  @ApplicationModuleListener
  fun onAgentAssignmentAccepted(event: AgentAssignmentAcceptedEvent) {
    val agent = agentRepository.findById(event.agentId)
    if (agent == null) {
      applicationEventPublisher.publishEvent(
        ErrorEvent(
          "agent-not-found",
          "onAgentAssignmentAccepted",
          "Agent ${event.agentId} not found.",
          LocalDateTime.now()
        )
      )
      return
    }

    if (agent.state != AgentState.PENDING) {
      applicationEventPublisher.publishEvent(
        ErrorEvent(
          "agent-not-pending",
          "onAgentAssignmentAccepted",
          "Agent ${event.agentId} is not in status 'PENDING', actual state: ${agent.state}.",
          LocalDateTime.now()
        )
      )
      return
    }

    agent.state = AgentState.ASSIGNED

    agentRepository.save(agent)
    applicationEventPublisher.publishEvent(AgentUpdatedEvent(agent.id, "Agent Assigned."))
  }
}