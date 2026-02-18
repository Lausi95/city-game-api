package net.lausi95.citygame

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

class Keycloak(
    private val restClient: RestClient,
    private val realmUrl: String,
    private val clientId: String,
    private val username: String,
    private val password: String,
) {

    private data class TokenResponse(
        @JsonProperty("access_token") val accessToken: String
    )

    fun getJwtToken(): String {
        val formData = LinkedMultiValueMap<String, String>()
        formData.add("grant_type", "password")
        formData.add("client_id", clientId)
        formData.add("username", username)
        formData.add("password", password)

        val tokenResponse = restClient.post()
            .uri("${realmUrl}/protocol/openid-connect/token")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .body(formData)
            .retrieve()
            .body<TokenResponse>()

        return tokenResponse?.accessToken ?: error("could not fetch access token")
    }
}
