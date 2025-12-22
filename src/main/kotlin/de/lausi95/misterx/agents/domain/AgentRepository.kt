package de.lausi95.misterx.agents.domain

import de.lausi95.misterx.agents.Agent

interface AgentRepository {

  fun save(agent: Agent)

  fun findAll(): List<Agent>
}
