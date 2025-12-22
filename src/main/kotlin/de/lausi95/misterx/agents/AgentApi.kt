package de.lausi95.misterx.agents

import de.lausi95.misterx.games.GameId
import org.springframework.security.access.prepost.PreAuthorize
import java.util.*

data class Agent(
  val id: AgentId,
  val gameId: GameId,
  val firstName: AgentFirstName,
  val lastName: AgentLastName,
  val phoneNumber: AgentPhoneNumber
)

data class AgentId(val value: String = UUID.randomUUID().toString())
data class AgentFirstName(val value: String)
data class AgentLastName(val value: String)
data class AgentPhoneNumber(val value: String)

data class CreateAgentCommand(
  val gameId: GameId,
  val firstName: AgentFirstName,
  val lastName: AgentLastName,
  val phoneNumber: AgentPhoneNumber
)

data class CreateAgentResult(
  val agentId: AgentId
)

data class AgentCreatedEvent(
  val agentId: AgentId
)

interface AgentApi {

  @PreAuthorize("hasAuthority('SCOPE_agents:create')")
  fun createAgent(command: CreateAgentCommand): CreateAgentResult

  @PreAuthorize("hasAuthority('SCOPE_agents:read')")
  fun getAgent(agentId: AgentId): Agent?

  @PreAuthorize("hasAuthority('SCOPE_agents:read')")
  fun getAgents(gameId: GameId): Agent
}
