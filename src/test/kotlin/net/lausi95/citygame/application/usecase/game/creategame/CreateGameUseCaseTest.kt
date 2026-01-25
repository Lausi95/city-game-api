package net.lausi95.citygame.application.usecase.game.creategame

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameRepository
import net.lausi95.citygame.domain.game.GameTitle
import org.assertj.core.api.Assertions
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class CreateGameUseCaseTest {

    @MockK(relaxed = true)
    private lateinit var gameRepository: GameRepository

    private lateinit var creteGameUseCase: CreateGameUseCase

    @BeforeEach
    fun setUp() {
        creteGameUseCase = CreateGameUseCase(gameRepository)
    }

    @Test
    fun `should create game when conditions meet`() {
        every { gameRepository.existsByTitle(any()) }.answers { false }
        val response = creteGameUseCase(
            CreateGameRequest(
                title = GameTitle("Foo")
            )
        )

        val game = slot<Game>()
        verify { gameRepository.save(capture(game)) }

        SoftAssertions.assertSoftly {
            it.assertThat(game.captured.id).isEqualTo(response.gameId)
            it.assertThat(game.captured.title).isEqualTo(GameTitle("Foo"))
        }
    }

    @Test
    fun `shoudl throw IllegalArgumentException, when game with title already exist`() {
        every { gameRepository.existsByTitle(any()) }.answers { true }
        val exception = assertThrows<IllegalArgumentException> {
            creteGameUseCase(
                CreateGameRequest(
                    title = GameTitle("Foo")
                )
            )
        }

        verify(exactly = 0) { gameRepository.save(any()) }
        Assertions.assertThat(exception.message).isEqualTo("Game with title already exist. Title: 'Foo'")
    }
}