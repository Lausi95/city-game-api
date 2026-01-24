package net.lausi95.citygame.infrastructure.postgresql.game

import net.lausi95.citygame.TestcontainersConfiguration
import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameRepository
import net.lausi95.citygame.domain.game.GameTitle
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.context.annotation.Import

@DataJpaTest
@Import(TestcontainersConfiguration::class)
class PostgresqlGameEntityRepositoryTest {

    @Autowired
    private lateinit var postgresqlGameEntityRepository: PostgresqlGameEntityRepository

    private lateinit var gameRepository: GameRepository

    @BeforeEach
    fun setUp() {
        gameRepository = PostgresqlGameRepository(postgresqlGameEntityRepository)
    }

    @Test
    fun `should save and load enitity`() {
        val game = Game(GameId.random(), GameTitle(":)"))
        gameRepository.save(game)

        val gameFromDb = gameRepository.findById(game.id)

        assertThat(gameFromDb).isNotNull()
        assertSoftly {
            it.assertThat(gameFromDb?.id).isEqualTo(game.id)
            it.assertThat(gameFromDb?.title).isEqualTo(game.title)
        }
    }

    @Test
    fun `on existsByTitle(), should return true, when game with that title was saved before`() {
        val someTitle = GameTitle("some title")
        gameRepository.save(Game(GameId.random(), someTitle))

        val existsByTitle = gameRepository.existsByTitle(someTitle)

        assertThat(existsByTitle).isTrue()
    }

    @Test
    fun `on existsByTitle(), should return false, when game with that title was never saved`() {
        val someTitle = GameTitle("some title")

        val existsByTitle = gameRepository.existsByTitle(someTitle)

        assertThat(existsByTitle).isFalse()
    }
}
