plugins {
    java
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"

    id("jacoco")
    pmd
}

group = "org.company"
version = "0.0.1-SNAPSHOT"
description = "user-service"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.springframework.security:spring-security-crypto")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    runtimeOnly("com.mysql:mysql-connector-j")

    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testCompileOnly("org.projectlombok:lombok")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testAnnotationProcessor("org.projectlombok:lombok")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

jacoco {
    toolVersion = "0.8.13"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)

    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) {
                    exclude(
                        "**/UserServiceApplication.class",
                        "**/domain/model/**",
                        "**/domain/exception/**",
                        "**/infrastructure/config/**",
                        "**/infrastructure/exception/**",
                        "**/infrastructure/in/mapper/**",
                        "**/infrastructure/in/request/**",
                        "**/infrastructure/in/response/**",
                        "**/infrastructure/in/response/**",
                        "**/infrastructure/out/hash/**",
                        "**/infrastructure/out/repository/entity/**",
                        "**/infrastructure/out/repository/mapper/**",
                        "**/MessageProvider.class"
                    )
                }
            }
        )
    )

    reports {
        xml.required.set(true)
        html.required.set(true)
        csv.required.set(false)
    }
}

tasks.jacocoTestCoverageVerification {

    classDirectories.setFrom(
        files(
            classDirectories.files.map {
                fileTree(it) {
                    exclude(
                        "**/UserServiceApplication.class",
                        "**/domain/model/**",
                        "**/domain/exception/**",
                        "**/infrastructure/config/**",
                        "**/infrastructure/exception/**",
                        "**/infrastructure/in/mapper/**",
                        "**/infrastructure/in/request/**",
                        "**/infrastructure/in/response/**",
                        "**/infrastructure/in/response/**",
                        "**/infrastructure/out/hash/**",
                        "**/infrastructure/out/repository/entity/**",
                        "**/infrastructure/out/repository/mapper/**",
                        "**/MessageProvider.class"
                    )
                }
            }
        )
    )

    violationRules {
        rule {
            limit {
                minimum = "0.80".toBigDecimal()
            }
        }
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestCoverageVerification)
}

pmd {
    toolVersion = "7.13.0"
    ruleSets = listOf(
        "category/java/bestpractices.xml",
        "category/java/errorprone.xml"
    )
}
