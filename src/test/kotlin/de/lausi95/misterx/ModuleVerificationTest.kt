package de.lausi95.misterx

import org.junit.jupiter.api.Test
import org.springframework.modulith.core.ApplicationModules

class ModuleVerificationTest {

  @Test
  fun `verify module integrity`() {
    ApplicationModules.of(MisterxApplication::class.java).verify()
  }
}