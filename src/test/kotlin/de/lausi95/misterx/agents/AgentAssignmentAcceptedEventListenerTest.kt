package de.lausi95.misterx.agents

import de.lausi95.misterx.ErrorEvent
import de.lausi95.misterx.TestcontainersConfiguration
import de.lausi95.misterx.agents.domain.AgentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.context.annotation.Import
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.Scenario
import org.springframework.test.context.ActiveProfiles

@ApplicationModuleTest(module = "agents")
@ActiveProfiles("test")
@Import(TestcontainersConfiguration::class)
class AgentAssignmentAcceptedEventListenerTest(
  private val agentRepository: AgentRepository
) {

  @Test
  fun `should update event state and publish 'AgentUpdatedEvent'`(scenario: Scenario) {
    val agent = randomAgent(state = AgentState.PENDING)
    agentRepository.save(agent)

    scenario.publish(AgentAssignmentAcceptedEvent(agent.id, agent.gameId))
      .andWaitForEventOfType(AgentUpdatedEvent::class.java)
      .matchingMappedValue(AgentUpdatedEvent::agentId, agent.id)
      .toArriveAndVerify {
        val updatedAgent = agentRepository.findById(agent.id) ?: Assertions.fail()
        assertThat(updatedAgent.state).isEqualTo(AgentState.ASSIGNED)
      }
  }

  @Test
  fun `should publish 'ErrorEvent', when referenced agent does not exist`(scenario: Scenario) {
    val agent = randomAgent(state = AgentState.PENDING)

    scenario.publish(AgentAssignmentAcceptedEvent(agent.id, agent.gameId))
      .andWaitForEventOfType(ErrorEvent::class.java)
      .toArrive()
  }
}