package de.lausi95.misterx.agents.domain

import de.lausi95.misterx.agents.AgentFirstName
import de.lausi95.misterx.agents.AgentId
import de.lausi95.misterx.agents.AgentLastName
import de.lausi95.misterx.agents.AgentPhoneNumber

data class Agent(
  val id: AgentId,
  val firstName: AgentFirstName,
  val lastName: AgentLastName,
  val phoneNumber: AgentPhoneNumber
)