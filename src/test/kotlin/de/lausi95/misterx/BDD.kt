package de.lausi95.misterx

import de.lausi95.misterx.agents.*
import net.datafaker.Faker

private val faker = Faker()

fun randomAgentId() = AgentId(faker.internet().uuid())

fun randomAgentFirstName() = AgentFirstName(faker.name().firstName())

fun randomAgentLastName() = AgentLastName(faker.name().lastName())

fun randomAgentPhoneNumber() = AgentPhoneNumber(faker.phoneNumber().phoneNumber())

fun randomCreateAgentCommand(
  agentFirstName: AgentFirstName = randomAgentFirstName(),
  agentLastName: AgentLastName = randomAgentLastName(),
  agentPhoneNumber: AgentPhoneNumber = randomAgentPhoneNumber()
) = CreateAgentCommand(
  agentFirstName,
  agentLastName,
  agentPhoneNumber
)
