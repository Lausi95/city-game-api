package de.lausi95.misterx.games

import de.lausi95.misterx.faker

fun randomGameId() = GameId(faker.internet().uuid())

fun randomGameTitle() = GameTitle(faker.internet().uuid())

fun randomGame(
  id: GameId = randomGameId(),
  title: GameTitle = randomGameTitle()
) = Game(id, title)
