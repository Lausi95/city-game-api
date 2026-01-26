package net.lausi95.citygame.application.usecase.game.getgame

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import net.lausi95.citygame.domain.game.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class GetGameUseCaseTest {

    @MockK
    private lateinit var gameRepository: GameRepository
    private lateinit var getGameUseCase: GetGameUseCase

    @BeforeEach
    fun setUp() {
        getGameUseCase = GetGameUseCase(gameRepository)
    }

    @Test
    fun `should return game when game with given id exists`() {
        val game = Game(GameId("some-game-id"), GameTitle("some-game-title"))
        every { gameRepository.findById(GameId("some-game-id")) }.answers { game }

        val result = getGameUseCase(GameId("some-game-id"))

        assertThat(result).isEqualTo(game)
    }

    @Test
    fun `should throw GameNotFoundException, when game with given id does not exist`() {
        every { gameRepository.findById(GameId("some-game-id")) }.answers { null }

        val exception = assertThrows<GameNotFoundException> {
            getGameUseCase(GameId("some-game-id"))
        }

        assertThat(exception.message).isEqualTo("Game not found: some-game-id")
    }
}
