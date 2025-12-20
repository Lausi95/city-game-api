package de.lausi95.misterx.agents.domain

interface AgentRepository {

  fun save(agent: Agent)

  fun findAll(): List<Agent>
}
