package de.lausi95.misterx.agents.adapter.web

import de.lausi95.misterx.agents.Agent

data class AgentCollection(
  val agents: List<AgentResource>,
  val links: Map<String, String>
) {

  constructor(agents: List<Agent>) : this(
    agents.map { AgentResource(it) },
    mapOf(
      "self" to "/agents"
    )
  )
}

data class AgentResource(
  val id: String,
  val firstName: String,
  val lastName: String,
  val phoneNumber: String,
  val links: Map<String, String>
) {

  constructor(agent: Agent) : this(
    agent.id.value,
    agent.firstName.value,
    agent.lastName.value,
    agent.phoneNumber.value,
    mapOf(
      "self" to "/agents/${agent.id.value}"
    )
  )
}
