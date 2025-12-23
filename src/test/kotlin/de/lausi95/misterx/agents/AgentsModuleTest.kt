package de.lausi95.misterx.agents

import de.lausi95.misterx.TestcontainersConfiguration
import de.lausi95.misterx.agents.domain.AgentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.context.annotation.Import
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.Scenario
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles

@ApplicationModuleTest
@WithMockUser(authorities = ["SCOPE_agents:create"])
@Import(TestcontainersConfiguration::class)
@ActiveProfiles("test")
class AgentsModuleTest(
  private val agentApi: AgentApi,
  private val agentRepository: AgentRepository
) {

  @Test
  @WithMockUser(authorities = [])
  fun test10() {
    val someCreateAgentCommand = randomCreateAgentCommand()
    assertThrows<AuthorizationDeniedException> {
      agentApi.createAgent(someCreateAgentCommand)
    }
  }

  @Test
  fun test9() {
    val someCreateAgentCommand = randomCreateAgentCommand()
    assertDoesNotThrow {
      agentApi.createAgent(someCreateAgentCommand)
    }
  }

  @Test
  fun test7(scenario: Scenario) {
    val command = randomCreateAgentCommand()
    scenario.stimulate { agentApi.createAgent(command) }
      .andWaitForEventOfType(AgentAssignmentRequestedEvent::class.java)
      .toArriveAndVerify { assertThat(it).isNotNull() }
  }

  @Test
  fun test5(scenario: Scenario) {
    val command = randomCreateAgentCommand()
    scenario.stimulate { agentApi.createAgent(command) }
      .andWaitForEventOfType(AgentUpdatedEvent::class.java)
      .toArriveAndVerify { assertThat(it).isNotNull() }
  }

  @Test
  fun test8() {
    val command = randomCreateAgentCommand()
    val result = agentApi.createAgent(command)

    assertThat(agentRepository.findAll()).anySatisfy { agent ->
      assertThat(agent.id).isEqualTo(result.agentId)
      assertThat(agent.firstName).isEqualTo(command.firstName)
      assertThat(agent.lastName).isEqualTo(command.lastName)
      assertThat(agent.phoneNumber).isEqualTo(command.phoneNumber)
      assertThat(agent.state).isEqualTo(AgentState.PENDING)
    }
  }

  @Test
  fun test4(scenario: Scenario) {
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
  fun test3(scenario: Scenario) {
    val agent = randomAgent(state = AgentState.PENDING)

    scenario.publish(AgentAssignmentAcceptedEvent(agent.id, agent.gameId))
      .andWaitForEventOfType(MisterxErrorEvent::class.java)
      .toArrive()
  }

  @Test
  fun test2(scenario: Scenario) {
    val agent = randomAgent(state = AgentState.PENDING)
    agentRepository.save(randomAgent())

    scenario.publish(AgentAssignmentRejectedEvent(agent.id, agent.gameId, "xxx"))
      .andWaitForEventOfType(MisterxErrorEvent::class.java)
      .toArrive()
  }

  @Test
  fun test1(scenario: Scenario) {
    val agent = randomAgent(state = AgentState.PENDING)
    agentRepository.save(agent)

    scenario.publish(AgentAssignmentRejectedEvent(agent.id, agent.gameId, "yyy"))
      .andWaitForEventOfType(AgentUpdatedEvent::class.java)
      .matchingMappedValue(AgentUpdatedEvent::agentId, agent.id)
      .toArriveAndVerify {
        val updatedAgent = agentRepository.findById(agent.id) ?: Assertions.fail()
        assertThat(updatedAgent.state).isEqualTo(AgentState.REJECTED)
      }
  }
}
