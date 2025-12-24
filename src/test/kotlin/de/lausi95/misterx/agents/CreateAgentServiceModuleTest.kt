package de.lausi95.misterx.agents

import de.lausi95.misterx.TestcontainersConfiguration
import de.lausi95.misterx.agents.domain.AgentRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.context.annotation.Import
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.Scenario
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles

@ApplicationModuleTest(module = "agents")
@WithMockUser(authorities = ["SCOPE_agents:create"])
@ActiveProfiles("test")
@Import(TestcontainersConfiguration::class)
class CreateAgentServiceModuleTest(
  private val createAgentService: CreateAgentService,
  private val agentRepository: AgentRepository
) {

  @Test
  @WithMockUser(authorities = [])
  fun `it should throw AuthorizationDeniedException, when authority is not present`() {
    val someCreateAgentCommand = randomCreateAgentCommand()
    assertThrows<AuthorizationDeniedException> {
      createAgentService.createAgent(someCreateAgentCommand)
    }
  }

  @Test
  fun `it should not throw AuthorizationDeniedException, when authority is present`() {
    val someCreateAgentCommand = randomCreateAgentCommand()
    assertDoesNotThrow {
      createAgentService.createAgent(someCreateAgentCommand)
    }
  }

  @Test
  fun `it should save agent in the repository, when agent is created successfully`() {
    val command = randomCreateAgentCommand()
    val result = createAgentService.createAgent(command)

    assertThat(agentRepository.findAll()).anySatisfy { agent ->
      assertThat(agent.id).isEqualTo(result.agentId)
      assertThat(agent.firstName).isEqualTo(command.firstName)
      assertThat(agent.lastName).isEqualTo(command.lastName)
      assertThat(agent.phoneNumber).isEqualTo(command.phoneNumber)
      assertThat(agent.state).isEqualTo(AgentState.PENDING)
    }
  }

  @Test
  fun `it should publish 'AgentAssignmentRequestedEvent', when agent is created successfully`(scenario: Scenario) {
    val command = randomCreateAgentCommand()
    scenario.stimulate { createAgentService.createAgent(command) }
      .andWaitForEventOfType(AgentAssignmentRequestedEvent::class.java)
      .toArriveAndVerify { assertThat(it).isNotNull() }
  }

  @Test
  fun `it should publish 'AgentUpdatedEvent', when agent is created successfully`(scenario: Scenario) {
    val command = randomCreateAgentCommand()
    scenario.stimulate { createAgentService.createAgent(command) }
      .andWaitForEventOfType(AgentUpdatedEvent::class.java)
      .toArriveAndVerify { assertThat(it).isNotNull() }
  }
}
