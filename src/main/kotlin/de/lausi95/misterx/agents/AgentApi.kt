package de.lausi95.misterx.agents

import org.springframework.security.access.prepost.PreAuthorize
import java.util.*

data class Agent(
  val id: AgentId,
  val firstName: AgentFirstName,
  val lastName: AgentLastName,
  val phoneNumber: AgentPhoneNumber
)

data class AgentId(val value: String = UUID.randomUUID().toString())
data class AgentFirstName(val value: String)
data class AgentLastName(val value: String)
data class AgentPhoneNumber(val value: String)

data class CreateAgentCommand(
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

  @PreAuthorize("hasAuthority('SCOPE_create-agent')")
  fun createAgent(command: CreateAgentCommand): CreateAgentResult

  @PreAuthorize("hasAuthority('SCOPE_get-agent')")
  fun getAgent(agentId: AgentId): Agent?

  @PreAuthorize("hasAuthority('SCOPE_get-agents')")
  fun getAgent(): Agent
}
