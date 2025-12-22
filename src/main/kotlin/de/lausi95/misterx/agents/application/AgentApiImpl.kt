package de.lausi95.misterx.agents.application

import de.lausi95.misterx.agents.*
import de.lausi95.misterx.agents.domain.AgentRepository
import de.lausi95.misterx.games.GameId
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
private class AgentApiImpl(
  private val applicationEventPublisher: ApplicationEventPublisher,
  private val agentRepository: AgentRepository
) : AgentApi {

  override fun createAgent(command: CreateAgentCommand): CreateAgentResult {
    val agentId = AgentId()
    val agent =
      Agent(agentId, command.gameId, command.firstName, command.lastName, command.phoneNumber)
    agentRepository.save(agent)

    applicationEventPublisher.publishEvent(AgentCreatedEvent(agentId))
    return CreateAgentResult(agentId)
  }

  override fun getAgent(agentId: AgentId): Agent? {
    TODO("Not yet implemented")
  }

  override fun getAgents(gameId: GameId): Agent {
    TODO("Not yet implemented")
  }
}
