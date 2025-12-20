package de.lausi95.misterx

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.modulith.Modulithic
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@SpringBootApplication
@EnableMethodSecurity
@Modulithic(systemName = "MisterX")
class MisterxApplication

fun main(args: Array<String>) {
  runApplication<MisterxApplication>(*args)
}
