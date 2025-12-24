package de.lausi95.misterx.agents.adapter.web

import com.ninjasquad.springmockk.MockkBean
import de.lausi95.misterx.agents.AgentApi
import de.lausi95.misterx.agents.CreateAgentResult
import de.lausi95.misterx.agents.randomAgent
import de.lausi95.misterx.agents.randomAgentId
import de.lausi95.misterx.games.randomGameId
import io.mockk.every
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper

@WebMvcTest(AgentController::class)
class AgentControllerTest(
  @Autowired private val mockMvc: MockMvc,
) {

  @MockkBean
  private lateinit var agentApi: AgentApi

  private val objectMapper = ObjectMapper()

  @Nested
  @DisplayName("POST /agents")
  inner class PostAgent {

    @Test
    fun `when endpoint is successfully called, returns 201 CREATED response with location header`() {
      val agentId = randomAgentId()
      val gameId = randomGameId()
      every { agentApi.createAgent(any()) }.returns(CreateAgentResult(agentId))

      val request = AgentController.CreateAgentRequest(gameId.value, "Foo", "Bar", "Baz")

      mockMvc.perform(
        post("/agents").with(jwt())
          .contentType("application/json")
          .content(objectMapper.writeValueAsString(request))
      ).andExpect(
        status().isCreated()
      ).andExpect(
        header().string("location", "/agents/" + agentId.value)
      )
    }
  }

  @Nested
  @Disabled
  @DisplayName("GET /agents/{agentId}")
  inner class GetAgent {

    @Test
    fun `when endpoint is called successfully, returns an agent resource`() {
      val agent = randomAgent()
      // every { agentApi.getAgent(agent.id) }.returns(agent)

      mockMvc.perform(get("/agents/{agentId}", agent.id.value).with(jwt()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(agent.id.value))
        .andExpect(jsonPath("$.firstName").value(agent.firstName.value))
        .andExpect(jsonPath("$.lastName").value(agent.lastName.value))
        .andExpect(jsonPath("$.phoneNumber").value(agent.phoneNumber.value))
        .andExpect(jsonPath("$.links.self").value("/agents/${agent.id.value}"))
    }

    @Test
    fun `when agent does not exist, responds with a 404 status code`() {
      // every { agentApi.getAgent(any()) }.returns(null)

      mockMvc.perform(get("/agents/{agentId}", randomAgentId().value).with(jwt()))
        .andExpect(status().isNotFound())
    }
  }

  @Nested
  @DisplayName("GET /agents")
  inner class GetAgents {

    @Test
    fun `when endpoint is called successfully, returns a list with agent resources`() {

    }
  }
}
