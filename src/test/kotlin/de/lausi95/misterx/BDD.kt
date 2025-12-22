package de.lausi95.misterx

import de.lausi95.misterx.agents.*
import de.lausi95.misterx.games.GameId
import de.lausi95.misterx.games.randomGameId
import net.datafaker.Faker

val faker = Faker()

fun randomAgentId() = AgentId(faker.internet().uuid())

fun randomAgentFirstName() = AgentFirstName(faker.name().firstName())

fun randomAgentLastName() = AgentLastName(faker.name().lastName())

fun randomAgentPhoneNumber() = AgentPhoneNumber(faker.phoneNumber().phoneNumber())

fun randomAgent(
  id: AgentId = randomAgentId(),
  gameId: GameId = randomGameId(),
  firstName: AgentFirstName = randomAgentFirstName(),
  lastName: AgentLastName = randomAgentLastName(),
  phoneNumber: AgentPhoneNumber = randomAgentPhoneNumber()
) = Agent(id, gameId, firstName, lastName, phoneNumber)

fun randomCreateAgentCommand(
  gameId: GameId = randomGameId(),
  agentFirstName: AgentFirstName = randomAgentFirstName(),
  agentLastName: AgentLastName = randomAgentLastName(),
  agentPhoneNumber: AgentPhoneNumber = randomAgentPhoneNumber()
) = CreateAgentCommand(gameId, agentFirstName, agentLastName, agentPhoneNumber)
