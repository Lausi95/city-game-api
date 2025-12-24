package de.lausi95.misterx.agents.application

import de.lausi95.misterx.agents.*
import de.lausi95.misterx.agents.domain.AgentRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service

@Primary
@Service
class CreateAgentServiceImpl(
  private val applicationEventPublisher: ApplicationEventPublisher,
  private val agentRepository: AgentRepository
) : CreateAgentService {

  override fun createAgent(command: CreateAgentCommand): CreateAgentResult {
    val agentId = AgentId()
    val agent = Agent(
      agentId,
      command.gameId,
      command.firstName,
      command.lastName,
      command.phoneNumber,
      AgentState.PENDING,
    )

    agentRepository.save(agent)
    applicationEventPublisher.publishEvent(AgentUpdatedEvent(agentId, "Agent Created."))

    applicationEventPublisher.publishEvent(AgentAssignmentRequestedEvent(agentId, command.gameId))

    return CreateAgentResult(agentId)
  }
}