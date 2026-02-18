package net.lausi95.citygame.integration

import net.lausi95.citygame.IntegrationTest
import net.lausi95.citygame.Keycloak
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.client.RestTestClient
import tools.jackson.databind.ObjectMapper

@IntegrationTest
class GameIntegrationTest {

    @Autowired
    private lateinit var restTestClient: RestTestClient

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var keycloak: Keycloak

    @Test
    fun name() {

        restTestClient.post()
            .uri("/games")
            .header("Authorization", "Bearer ${keycloak.getJwtToken()}")
            .contentType(MediaType.APPLICATION_JSON)
            .body(
                """
                {
                  "title": "Hallo :)"
                }
                """.trimIndent()
            )
            .exchange()
            .expectStatus().isCreated()
    }
}
