package de.lausi95.misterx.agents

import de.lausi95.misterx.TestcontainersConfiguration
import de.lausi95.misterx.agents.domain.AgentRepository
import de.lausi95.misterx.randomCreateAgentCommand
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.context.annotation.Import
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.Scenario
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.security.test.context.support.WithMockUser

@ApplicationModuleTest
@Import(TestcontainersConfiguration::class)
class AgentApiTest(
  private val agentApi: AgentApi,
  private val agentRepository: AgentRepository
) {

  @Nested
  @DisplayName("createAgent()")
  @WithMockUser(authorities = ["SCOPE_create-agent"])
  inner class CreateAgent {

    @Test
    @WithMockUser(authorities = [])
    fun `when required scope is not present, it should deny access`() {
      val someCreateAgentCommand = randomCreateAgentCommand()
      assertThrows<AuthorizationDeniedException> {
        agentApi.createAgent(someCreateAgentCommand)
      }
    }

    @Test
    fun `when required scope is present, it should allow access`() {
      val someCreateAgentCommand = randomCreateAgentCommand()
      assertDoesNotThrow {
        agentApi.createAgent(someCreateAgentCommand)
      }
    }

    @Test
    fun `when agent was created, it should publish 'AgentCreatedEvent'`(scenario: Scenario) {
      val command = randomCreateAgentCommand()
      scenario.stimulate { agentApi.createAgent(command) }
        .andWaitForEventOfType(AgentCreatedEvent::class.java)
        .toArriveAndVerify { assertThat(it).isNotNull() }
    }

    @Test
    fun `when agent was created, it should be present in the repository`() {
      val command = randomCreateAgentCommand()
      val result = agentApi.createAgent(command)

      assertThat(agentRepository.findAll()).anySatisfy { agent ->
        assertThat(agent.id).isEqualTo(result.agentId)
        assertThat(agent.firstName).isEqualTo(command.firstName)
        assertThat(agent.lastName).isEqualTo(command.lastName)
        assertThat(agent.phoneNumber).isEqualTo(command.phoneNumber)
      }
    }
  }
}
