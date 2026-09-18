# CLAUDE.md — Contexto del proyecto

## Resumen
- Tipo de proyecto: microservicio (single-module)
- Dominio/propósito: gestión de empleados — registro (`/api/v1/employee/registry`) y validación de credenciales/login (`/api/v1/employee/validate`)
- Stack: Java 17, Spring Boot 4.1.1, Gradle (Kotlin DSL)
- Módulos/servicios: único módulo `user-service`

## Arquitectura actual
- Estilo arquitectónico identificado: hexagonal (ports & adapters) — `domain` / `application` (con `port/in` y `port/out`) / `infrastructure` (`in`, `out`, `config`, `exception`)
- Estructura de paquetes de alto nivel:
  ```
  org.company.user
  ├── domain/          (model: Employee, EmployeeCredentials — sin dependencias de Spring)
  ├── application/      (EmployeeService + port/in, port/out — findByEmail retorna Optional<Employee>)
  └── infrastructure/
      ├── in/            (EmployeeController, mapper MapStruct, request)
      ├── out/           (hash, repository, MessageProvider)
      ├── config/        (SecurityConfig, PasswordConfig, LocaleConfig)
      ├── exception/      (GlobalExceptionHandler, con logging vía SLF4J)
      └── util/          (UtilDate)
  ```
- Patrones de diseño en uso: Ports & Adapters (hexagonal), Repository (`EmployeeRepository`/`EmployeeRepositoryAdapter`), mapeo generado con **MapStruct** (`EmployeeInMapper`, `EmployeeEntityMapper` — interfaces `@Mapper(componentModel = "spring")`).

## Convenciones del proyecto
- Convención de nombres de paquetes/clases: `Employee*` de forma consistente (corregido en el review 2026-09-17: `EmployerController`→`EmployeeController`, `EmployerRequest`→`EmployeeRequest`, `EmployeeValidation`→`EmployeeCredentials`, `EmployeJpaRepository`→`EmployeeJpaRepository`).
- Manejo de excepciones: centralizado vía `@RestControllerAdvice` (`GlobalExceptionHandler`), con `ErrorCode` enum mapeado a `HttpStatus`, y logging (`@Slf4j`) en el handler genérico.
- Persistencia: Spring Data JPA + MySQL, `ddl-auto=validate` + **Flyway** (`src/main/resources/db/migration`) como fuente de verdad del esquema, con `email UNIQUE NOT NULL`.
- Testing: JUnit 5 + Mockito, tests unitarios con mocks (sin `@SpringBootTest`/`@WebMvcTest`); cobertura mínima 80% vía Jacoco, con exclusiones acotadas a clases realmente no testeables (`domain/model`, `infrastructure/config`, `request`/`response`, `out/hash`, entidades JPA); mappers y `GlobalExceptionHandler` sí están dentro de la medición y tienen tests dedicados.
- Seguridad: OAuth2 Resource Server (JWT), scope `SCOPE_user-service.write` requerido en `/api/**`; hashing de password con BCrypt; credenciales de BD vía variables de entorno (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`), nunca hardcodeadas.
- Calidad estática: PMD, SpotBugs (+ FindSecBugs) y Spotless configurados y activos en el build. `pmdMain`/`spotbugsMain` están en verde; `pmdTest`/`spotbugsTest` tienen deuda preexistente (no introducida por el review de arquitectura) — ver detalle en `clean-architectural-proposal.md`.

## Decisiones arquitectónicas relevantes
- 2026-09-17 — Se mantiene hexagonal (ports & adapters) en vez de migrar a Clean Architecture: el proyecto ya lo aplicaba de forma coherente y tiene un solo agregado de dominio (`Employee`); capas concéntricas adicionales serían sobre-ingeniería para este tamaño de servicio.
- 2026-09-17 — Se resolvió el naming Employer/Employee como error (no como distinción B2B intencional), confirmado con el usuario.

## Historial de reviews
- 2026-09-17 — Review arquitectónico ejecutado (skill java-spring-architecture-review). Hallazgos: NPE no controlado en validación de login, exception handler sin logging, credenciales de BD en texto plano, cobertura de tests engañosa por exclusiones amplias de Jacoco, ausencia de migraciones de esquema, naming inconsistente Employer/Employee, modelos anémicos con mapeo manual triplicado, mensaje de validación de CP incorrecto, ausencia total de logging, tipado laxo en el controller. Cambios aplicados: todos (Crítico a Bajo) — ver `clean-architectural-proposal.md` sección "Cambios aplicados".
