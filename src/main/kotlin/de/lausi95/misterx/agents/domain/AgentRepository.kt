package de.lausi95.misterx.agents.domain

import de.lausi95.misterx.agents.Agent
import de.lausi95.misterx.agents.AgentId

interface AgentRepository {

  fun save(agent: Agent)

  fun findById(agentId: AgentId): Agent?

  fun findAll(): List<Agent>
}
