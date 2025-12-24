package de.lausi95.misterx.agents

import de.lausi95.misterx.games.GameId
import org.springframework.modulith.ApplicationModule
import org.springframework.modulith.PackageInfo
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@ApplicationModule(
  id = "agents",
  displayName = "Agents"
)
@PackageInfo
class ModuleMetadata {}

enum class AgentState {
  PENDING,
  ASSIGNED,
  REJECTED
}

data class Agent(
  val id: AgentId,
  val gameId: GameId,
  val firstName: AgentFirstName,
  val lastName: AgentLastName,
  val phoneNumber: AgentPhoneNumber,
  var state: AgentState,
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
  val agentId: AgentId,
)

data class AgentAssignmentRequestedEvent(
  val agentId: AgentId,
  val gameId: GameId,
)

data class AgentAssignmentAcceptedEvent(
  val agentId: AgentId,
  val gameId: GameId,
)

data class AgentAssignmentRejectedEvent(
  val agentId: AgentId,
  val gameId: GameId,
  val reason: String,
)

data class AgentUpdatedEvent(
  val agentId: AgentId,
  val message: String,
)

@Component
class AgentApi(private val createAgentService: CreateAgentService) : CreateAgentService by createAgentService

interface CreateAgentService {

  @Transactional
  @PreAuthorize("hasAuthority('SCOPE_agents:create')")
  fun createAgent(command: CreateAgentCommand): CreateAgentResult
}
