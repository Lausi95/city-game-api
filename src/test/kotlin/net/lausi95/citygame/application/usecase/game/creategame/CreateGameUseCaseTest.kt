package net.lausi95.citygame.application.usecase.game.creategame

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.slot
import io.mockk.verify
import net.lausi95.citygame.bdd.random
import net.lausi95.citygame.domain.Tenant
import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameRepository
import net.lausi95.citygame.domain.game.GameTitle
import net.lausi95.citygame.domain.game.GameTitleAlreadyExistsException
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
        val tenant = Tenant.random()
        every { gameRepository.existsByTitle(any(), tenant) }.answers { false }
        val response = creteGameUseCase(
            CreateGameCommand(
                title = GameTitle("Foo"),
                tenant = tenant
            )
        )

        val game = slot<Game>()
        verify { gameRepository.save(capture(game), tenant) }

        SoftAssertions.assertSoftly {
            it.assertThat(game.captured.id).isEqualTo(response.gameId)
            it.assertThat(game.captured.title).isEqualTo(GameTitle("Foo"))
        }
    }

    @Test
    fun `should throw GameTitleAlreadyExistsException, when game with title already exist`() {
        val tenant = Tenant.random()
        every { gameRepository.existsByTitle(any(), tenant) }.answers { true }
        val exception = assertThrows<GameTitleAlreadyExistsException> {
            creteGameUseCase(
                CreateGameCommand(
                    title = GameTitle("Foo"),
                    tenant = tenant
                )
            )
        }

        verify(exactly = 0) { gameRepository.save(any(), tenant) }
        Assertions.assertThat(exception.message).isEqualTo("Game title already exist: Foo")
    }
}