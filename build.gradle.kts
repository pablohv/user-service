plugins {
    java
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"

    // Calidad de código
    jacoco
    pmd
    id("com.github.spotbugs") version "6.2.4"
    id("com.diffplug.spotless") version "7.2.1"
    id("org.sonarqube") version "6.0.1.5171"

    // Obtener info de GIT
    id("org.ajoberstar.grgit") version "5.3.0"
}

group = "org.company"
version = "1.0.0-${grgit.head().abbreviatedId}" // por cada commit se crea una versión diferente
description = "user-service"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

// Configuración modular: cada archivo de gradle/ tiene una única responsabilidad.
// Convención de nombres: <dominio>-<responsabilidad>.gradle (kebab-case)
listOf(
    "project-dependencies",
    "quality-coverage-exclusions", // debe ir antes de quality-jacoco y quality-sonar
    "quality-jacoco",
    "quality-sonar",
    "quality-spotbugs",
    "quality-pmd",
    "quality-spotless",
).forEach { apply(from = "gradle/$it.gradle") }
