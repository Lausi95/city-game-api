plugins {
  kotlin("jvm") version "2.2.10"
  kotlin("plugin.spring") version "2.2.10"
  kotlin("plugin.jpa") version "2.2.10"

  id("org.springframework.boot") version "4.0.1"
  id("io.spring.dependency-management") version "1.1.7"
}

group = "de.lausi95"
version = "0.0.1-SNAPSHOT"
description = "misterx"

java {
  toolchain {
    languageVersion = JavaLanguageVersion.of(21)
  }
}

repositories {
  gradlePluginPortal()
  mavenCentral()
}

extra["springModulithVersion"] = "2.0.1"
extra["datafakerVersion"] = "2.5.3"

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("org.springframework.boot:spring-boot-starter-actuator")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  // implementation("org.springframework.boot:spring-boot-starter-kafka")
  implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")
  implementation("org.springframework.boot:spring-boot-starter-flyway")
  implementation("org.springframework.boot:spring-boot-starter-validation")

  implementation("org.springframework.modulith:spring-modulith-starter-jpa")

  implementation("org.flywaydb:flyway-database-postgresql")
  implementation("org.jetbrains.kotlin:kotlin-reflect")

  developmentOnly("org.springframework.boot:spring-boot-docker-compose")

  runtimeOnly("org.postgresql:postgresql")
  runtimeOnly("org.springframework.modulith:spring-modulith-actuator")
  runtimeOnly("org.springframework.modulith:spring-modulith-events-jpa")
  runtimeOnly("org.springframework.modulith:spring-modulith-observability")

  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(module = "mockito-junit-jupiter")
    exclude(module = "mockito-core")
  }
  testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
  testImplementation("org.springframework.boot:spring-boot-starter-actuator-test")
  testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
  testImplementation("org.springframework.boot:spring-boot-starter-kafka-test")
  testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.springframework.modulith:spring-modulith-starter-test")
  testImplementation("org.testcontainers:testcontainers-junit-jupiter")
  testImplementation("org.testcontainers:testcontainers-kafka")
  testImplementation("org.testcontainers:testcontainers-postgresql")
  testImplementation("net.datafaker:datafaker:${property("datafakerVersion")}")

  testImplementation("io.mockk:mockk:1.14.7")
  testImplementation("com.ninja-squad:springmockk:5.0.1")

  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.modulith:spring-modulith-bom:${property("springModulithVersion")}")
  }
}

kotlin {
  compilerOptions {
    freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
  }
}

allOpen {
  annotation("jakarta.persistence.Entity")
  annotation("jakarta.persistence.MappedSuperclass")
  annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
  useJUnitPlatform()
}
