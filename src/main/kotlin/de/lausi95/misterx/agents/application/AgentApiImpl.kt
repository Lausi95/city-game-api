package de.lausi95.misterx.agents.application

import de.lausi95.misterx.agents.*
import de.lausi95.misterx.agents.domain.Agent
import de.lausi95.misterx.agents.domain.AgentRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class AgentApiImpl(
  private val applicationEventPublisher: ApplicationEventPublisher,
  private val agentRepository: AgentRepository
) : AgentApi {

  override fun createAgent(command: CreateAgentCommand): CreateAgentResult {
    val agentId = AgentId()
    val agent = Agent(agentId, command.firstName, command.lastName, command.phoneNumber)
    agentRepository.save(agent)

    applicationEventPublisher.publishEvent(AgentCreatedEvent(agentId))
    return CreateAgentResult(agentId)
  }
}
