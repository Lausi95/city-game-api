package de.lausi95.misterx.agents.adapter.postgresql

import de.lausi95.misterx.agents.AgentFirstName
import de.lausi95.misterx.agents.AgentId
import de.lausi95.misterx.agents.AgentLastName
import de.lausi95.misterx.agents.AgentPhoneNumber
import de.lausi95.misterx.agents.domain.Agent
import de.lausi95.misterx.agents.domain.AgentRepository
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

@Entity
@Table(name = "agent")
data class AgentEntity(

  @Id
  @Column(name = "id")
  val id: String,

  @Column(name = "first_name", nullable = false)
  val firstName: String,

  @Column(name = "last_name", nullable = false)
  val lastName: String,

  @Column(name = "phone_number", nullable = false)
  val phoneNumber: String,
) {

  constructor(agent: Agent) : this(
    agent.id.value,
    agent.firstName.value,
    agent.lastName.value,
    agent.phoneNumber.value
  )

  fun toAgent(): Agent = Agent(
    AgentId(id),
    AgentFirstName(firstName),
    AgentLastName(lastName),
    AgentPhoneNumber(phoneNumber)
  )
}

interface AgentEntityRepository : JpaRepository<AgentEntity, String>

@Component
class PostgresAgentRepository(private val agentEntityRepository: AgentEntityRepository) :
  AgentRepository {

  override fun save(agent: Agent) {
    val entity = AgentEntity(agent)
    agentEntityRepository.save(entity)
  }

  override fun findAll(): List<Agent> {
    return agentEntityRepository.findAll()
      .map { it.toAgent() }
  }
}
