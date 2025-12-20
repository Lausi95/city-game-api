package de.lausi95.misterx

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class MisterxApplicationTests {

  @Test
  fun contextLoads() {
  }

}
