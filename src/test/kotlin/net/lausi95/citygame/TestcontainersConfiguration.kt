package net.lausi95.citygame

import dasniko.testcontainers.keycloak.KeycloakContainer
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.test.context.DynamicPropertyRegistrar
import org.springframework.web.client.RestClient
import org.testcontainers.postgresql.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer {
        return PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
    }

    @Bean
    fun keycloakContainer(): KeycloakContainer {
        return KeycloakContainer()
            .withRealmImportFile("/keycloak.json")
    }

    @Bean
    fun registerKeycloakProperties(
        keycloakContainer: KeycloakContainer
    ): DynamicPropertyRegistrar {
        return DynamicPropertyRegistrar {
            it.add("keycloak-realm") { "${keycloakContainer.authServerUrl}/realms/test-realm" }
        }
    }

    @Bean
    fun keycloak(
        @Value($$"${keycloak-realm}") realm: String,
        @Value($$"${keycloak.client_id}") client: String,
        @Value($$"${keycloak.username}") username: String,
        @Value($$"${keycloak.password}") password: String,
    ): Keycloak {
        return Keycloak(RestClient.create(), realm, client, username, password)
    }
}
