package de.lausi95.misterx.agents

import de.lausi95.misterx.faker
import de.lausi95.misterx.games.GameId
import de.lausi95.misterx.games.randomGameId

fun randomAgentId() = AgentId(faker.internet().uuid())

fun randomAgentFirstName() = AgentFirstName(faker.name().firstName())

fun randomAgentLastName() = AgentLastName(faker.name().lastName())

fun randomAgentPhoneNumber() = AgentPhoneNumber(faker.phoneNumber().phoneNumber())

fun randomAgent(
  id: AgentId = randomAgentId(),
  gameId: GameId = randomGameId(),
  firstName: AgentFirstName = randomAgentFirstName(),
  lastName: AgentLastName = randomAgentLastName(),
  phoneNumber: AgentPhoneNumber = randomAgentPhoneNumber(),
  state: AgentState = AgentState.ASSIGNED,
) = Agent(id, gameId, firstName, lastName, phoneNumber, state)

fun randomCreateAgentCommand(
  gameId: GameId = randomGameId(),
  agentFirstName: AgentFirstName = randomAgentFirstName(),
  agentLastName: AgentLastName = randomAgentLastName(),
  agentPhoneNumber: AgentPhoneNumber = randomAgentPhoneNumber()
) = CreateAgentCommand(gameId, agentFirstName, agentLastName, agentPhoneNumber)
