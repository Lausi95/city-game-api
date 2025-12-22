package de.lausi95.misterx.agents.adapter.web

import de.lausi95.misterx.agents.*
import de.lausi95.misterx.games.GameId
import jakarta.validation.Valid
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/agents")
class AgentController(
  private val agentApi: AgentApi
) {

  data class CreateAgentRequest(

    @NotNull
    @NotEmpty
    var firstName: String,

    @NotNull
    @NotEmpty
    var gameId: String,

    @NotNull
    @NotEmpty
    var lastName: String,

    @NotNull
    @NotEmpty
    var phoneNumber: String
  )

  @PostMapping
  fun createAgent(@Valid @RequestBody request: CreateAgentRequest): ResponseEntity<Unit> {
    val result = agentApi.createAgent(
      CreateAgentCommand(
        firstName = AgentFirstName(request.firstName),
        gameId = GameId(request.gameId),
        lastName = AgentLastName(request.lastName),
        phoneNumber = AgentPhoneNumber(request.phoneNumber)
      )
    )

    val uri = ServletUriComponentsBuilder.fromPath("/agents/{agentId}").build(result.agentId.value)
    return ResponseEntity.created(uri).build()
  }

  @GetMapping("/{agentId}")
  fun getAgent(@PathVariable agentId: String): ResponseEntity<AgentResource> {
    val agent = agentApi.getAgent(AgentId(agentId)) ?: return ResponseEntity.notFound().build()
    return ResponseEntity.ok(AgentResource(agent))
  }
}
