package de.citygame

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules

class ModuleVerificationTest {

  @Test
  fun `verify module integrity`() {
    ApplicationModules.of(CityGameApplication::class.java).verify()
  }
}