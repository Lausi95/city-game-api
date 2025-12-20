package de.lausi95.misterx.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.DefaultSecurityFilterChain

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
class SecurityConfiguration {

  @Bean
  fun securityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain? = http
    .authorizeHttpRequests {
      it.requestMatchers("/v3/api-docs/**").permitAll()
        .requestMatchers("/actuator/**").permitAll()
        .requestMatchers("/swagger-ui/**").permitAll()
        .anyRequest().authenticated()
    }
    .oauth2ResourceServer { resourceServer ->
      resourceServer.jwt { jwt ->
        jwt.jwtAuthenticationConverter(
          JwtAuthenticationConverter()
        )
      }
    }
    .build()
}