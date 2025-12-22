package de.lausi95.misterx.games

import de.lausi95.misterx.TestcontainersConfiguration
import de.lausi95.misterx.games.domain.GameRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.context.annotation.Import
import org.springframework.modulith.test.ApplicationModuleTest
import org.springframework.modulith.test.Scenario
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.security.test.context.support.WithMockUser

@ApplicationModuleTest
@Import(TestcontainersConfiguration::class)
@WithMockUser(authorities = ["SCOPE_games:create"])
class CreateGameServiceTest(
  private val createGameService: CreateGameService,
  private val gameRepository: GameRepository
) {

  @Test
  @WithMockUser(authorities = [])
  fun `when the required authority is not present, it denies the access`() {
    assertThrows<AuthorizationDeniedException> {
      createGameService.createGame(randomGameTitle())
    }
  }

  @Test
  fun `when the correct authority is given, it allows access`() {
    assertDoesNotThrow {
      createGameService.createGame(randomGameTitle())
    }
  }

  @Test
  fun `when a game is created, it should be in the repository`() {
    val gameTitle = randomGameTitle()

    val gameId = createGameService.createGame(gameTitle)

    val games = gameRepository.findAll()
    assertThat(games).isNotEmpty().anySatisfy {
      assertThat(it.id).isEqualTo(gameId)
      assertThat(it.title).isEqualTo(gameTitle)
    }
  }

  @Test
  fun `when a game is created, it should publish a 'GameCreatedEvent'`(scenario: Scenario) {
    scenario.stimulate { createGameService.createGame(randomGameTitle()) }
      .andWaitForEventOfType(GameCreatedEvent::class.java)
      .toArriveAndVerify {
        assertThat(gameRepository.existsById(it.id)).isTrue()
      }
  }

  @Test
  fun `when trying to create a game, whos title already exists, throw GameTitleAlreadyExistsException`() {
    val gameTitle = randomGameTitle()
    assertDoesNotThrow { createGameService.createGame(gameTitle) }
    assertThrows<GameAlreadyExistsException> { createGameService.createGame(gameTitle) }
  }
}
