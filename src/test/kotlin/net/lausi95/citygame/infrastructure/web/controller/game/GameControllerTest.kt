package net.lausi95.citygame.infrastructure.web.controller.game

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.slot
import io.mockk.verify
import net.lausi95.citygame.application.usecase.creategame.CreateGameRequest
import net.lausi95.citygame.application.usecase.creategame.CreateGameResponse
import net.lausi95.citygame.application.usecase.creategame.CreateGameUseCase
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameTitle
import org.assertj.core.api.Assertions.assertThat
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@WebMvcTest(GameController::class)
class GameControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockkBean
    private lateinit var createGameUseCase: CreateGameUseCase

    @Test
    fun `should map pass request to use case and responds with 201 and location header with id from use case`() {
        val createGameResponse = CreateGameResponse(
            gameId = GameId("some-game-id")
        )
        every { createGameUseCase(any()) }.answers { createGameResponse }

        mockMvc.post("/games") {
            contentType = MediaType.APPLICATION_JSON
            with(jwt())
            content = /* language=json */ """
                {
                    "title": "City-Game Luckenwalde 2026"
                }
            """.trimIndent()
        }.andExpect {
            status { isCreated() }
            header {
                stringValues("location", "http://localhost/games/some-game-id")
            }
        }

        val createGameRequest = slot<CreateGameRequest>()
        verify { createGameUseCase(capture(createGameRequest)) }

        assertThat(createGameRequest.captured.title).isEqualTo(GameTitle("City-Game Luckenwalde 2026"))
    }

    @Test
    fun `should respond with bad request, when title is not present`() {
        mockMvc.post("/games") {
            contentType = MediaType.APPLICATION_JSON
            with(jwt())
            content = /* language=json */ """
                {
                }
            """.trimIndent()
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.details", equalTo("Validation Error"))
            jsonPath("$.title", equalTo("'title' cannot be null or empty"))
        }
    }

    @Test
    fun `should respond with bad request, when title is empty string`() {
        mockMvc.post("/games") {
            contentType = MediaType.APPLICATION_JSON
            with(jwt())
            content = /* language=json */ """
                {
                    "title": ""
                }
            """.trimIndent()
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.details", equalTo("Validation Error"))
            jsonPath("$.title", equalTo("'title' cannot be null or empty"))
        }
    }

    @Test
    fun `should response with bad request, when request is no valid json`() {
        mockMvc.post("/games") {
            contentType = MediaType.APPLICATION_JSON
            with(jwt())
            content = """
                    "title": ""
                }
            """.trimIndent()
        }.andExpect {
            status { isBadRequest() }
            jsonPath("$.details", equalTo("Input Error: Invalid JSON"))
        }
    }
}
