# Review arquitectónico — user-service

**Fecha:** 2026-09-17
**Tipo de proyecto:** microservicio (single-module)
**Stack:** Java 17, Spring Boot 4.1.1, Gradle (Kotlin DSL)
**Estilo arquitectónico:** hexagonal (ports & adapters), ya aplicado de forma consistente antes de este review.

## Tabla de hallazgos

| Severidad | Ubicación | Error | Causa | Solución propuesta |
|---|---|---|---|---|
| Crítico | `EmployeeService.validateEmployee` → `EmployeeRepositoryAdapter.findByEmail` → `EmployeeEntityMapper.entityToEmploy` | Login con email inexistente lanzaba `NullPointerException` en vez de devolver `EMPLOYEE_INVALID_CREDENTIALS` (422) | `EmployeJpaRepository.findByEmail` retornaba `null` si no había match, y `entityToEmploy(null)` accedía a `entity.getFirstName()` sin chequeo | Cambiar `findByEmail` a `Optional<EmployeeEntity>`; propagar `Optional<Employee>` en el puerto/adapter; lanzar `EmployeeException(EMPLOYEE_INVALID_CREDENTIALS)` si está vacío |
| Crítico | `GlobalExceptionHandler.handleGenericErrors()` | Cualquier excepción no controlada se descartaba sin loguearse — 0 usos de `Logger`/SLF4J en todo `src/main` | El `@ExceptionHandler(Exception.class)` ni siquiera recibía el parámetro `Exception ex`; no había ningún logger en el proyecto | Recibir `final Exception ex`, loguear con `log.error("Unhandled exception", ex)` antes de responder |
| Crítico | `src/main/resources/application.properties` (bloque MySQL) | Credenciales de base de datos (`root`/`admin`) hardcodeadas y versionadas en el repo | No se usaban variables de entorno ni secret manager | Externalizar a `${DB_USERNAME}`/`${DB_PASSWORD}` vía variables de entorno |
| Alto | `build.gradle.kts` (exclusiones de Jacoco) | La cobertura del 80% era engañosa: excluía justo las clases con más riesgo (`EmployeeEntityMapper`, `EmployeeInMapper`, `GlobalExceptionHandler`), sin tests dedicados para ninguna | Lista de exclusión demasiado amplia, sin tests manuales compensatorios | Reducir exclusiones a lo genuinamente no testeable; agregar tests para mappers y exception handler |
| Alto | `build.gradle.kts` / `src/main/resources` | No había herramienta de migraciones de esquema (Flyway/Liquibase) pese a `ddl-auto=validate`; tampoco existía `UNIQUE` explícito sobre `email` | El esquema se gestionaba fuera de control de versiones | Agregar Flyway con `V1__create_employees_table.sql`, incluyendo `email UNIQUE NOT NULL` |
| Alto | `EmployerController`, `EmployerRequest` vs. resto de la capa (`EmployeeService`, `EmployeeRepository`, `EmployeeEntity`...) | Naming inconsistente: solo 2 clases usaban "Employer" mientras las 15 restantes usaban "Employee" para el mismo dominio; más typos (`Employe`, `Employ`) | Falta de convención de nombres aplicada de forma consistente | Renombrar a `EmployeeController`, `EmployeeRequest`, `EmployeeJpaRepository`, `entityToEmployee` |
| Medio | `domain/model/Employee.java`, `domain/model/EmployeeValidation.java`, mappers `in`/`out` | Modelos de dominio anémicos y mapeo manual triplicado (DTO → dominio → entidad) de 11 campos casi idénticos | No se usó un mapper generado; alto riesgo de desalineación silenciosa | Introducir MapStruct para `EmployeeInMapper`/`EmployeeEntityMapper` |
| Medio | `src/main/resources/messages_en.properties` (`VALIDATION_CP`) | Mensaje en inglés decía "10 digits" cuando el regex real exige 5 dígitos; la versión en español ya decía "5 dígitos" | Copy-paste del mensaje `VALIDATION_PHONE` sin corregir | Corregir a "CP number should have 5 digits" |
| Medio | Proyecto completo | Cero logging de negocio/observabilidad | No se configuró SLF4J en ninguna clase | Agregar `@Slf4j` en `EmployeeService`, `EmployeeRepositoryAdapter` y `GlobalExceptionHandler` |
| Bajo | `EmployerController.saveEmployee` / `validateEmployee` | Retornaban `ResponseEntity<?>` en vez de un tipo concreto | Tipado laxo innecesario | Cambiar a `ResponseEntity<Void>` |

Capas revisadas y limpias, sin hallazgos artificiales agregados: `SecurityConfig`, `ErrorCode`/`EmployeeException`, `UtilDate`, `PasswordConfig`/`PasswordHashAdapter`.

## Propuesta de arquitectura objetivo

**Estilo elegido: mantener hexagonal (ports & adapters)**, no migrar a Clean Architecture ni a layered puro. El proyecto ya lo aplicaba de forma coherente (dominio sin dependencias de Spring, casos de uso vía `port/in`, infraestructura vía `port/out`); para un microservicio de un solo agregado (`Employee`), introducir capas concéntricas adicionales sería sobre-ingeniería. Lo que faltaba no era el estilo, sino corregir naming, cerrar el hueco de test/observabilidad y reducir el mapeo manual duplicado.

**Patrones de diseño aplicados:**
- **MapStruct** en lugar de mapeo manual campo a campo → elimina el riesgo de desalineación silenciosa en `EmployeeInMapper`/`EmployeeEntityMapper`.
- **Optional en los puertos de salida** (`EmployeeRepository.findByEmail(String): Optional<Employee>`) → resuelve el NPE de login de forma idiomática.
- **Migraciones versionadas (Flyway)** como infraestructura-como-código.

Árbol final:

```
user-service/
├── src/main/java/org/company/user/
│   ├── UserServiceApplication.java
│   ├── domain/
│   │   ├── model/
│   │   │   ├── Employee.java
│   │   │   └── EmployeeCredentials.java
│   │   └── exception/
│   │       ├── EmployeeException.java
│   │       └── ErrorCode.java
│   ├── application/
│   │   ├── EmployeeService.java
│   │   └── port/
│   │       ├── in/EmployeeUseCasePort.java
│   │       └── out/
│   │           ├── EmployeeRepository.java   (Optional<Employee>)
│   │           └── PasswordHash.java
│   └── infrastructure/
│       ├── config/ (SecurityConfig, PasswordConfig, LocaleConfig)
│       ├── exception/GlobalExceptionHandler.java (con logging)
│       ├── in/
│       │   ├── EmployeeController.java
│       │   ├── mapper/EmployeeInMapper.java   (MapStruct)
│       │   ├── request/EmployeeRequest.java, EmployeeCredentialsRequest.java
│       │   └── response/ValidationErrorResponse.java
│       ├── out/
│       │   ├── hash/PasswordHashAdapter.java
│       │   └── repository/
│       │       ├── EmployeeJpaRepository.java
│       │       ├── EmployeeRepositoryAdapter.java
│       │       ├── entity/EmployeeEntity.java
│       │       └── mapper/EmployeeEntityMapper.java  (MapStruct)
│       └── util/UtilDate.java
├── src/main/resources/
│   ├── db/migration/V1__create_employees_table.sql
│   ├── application.properties (credenciales vía variables de entorno)
│   └── messages_en.properties / messages_es.properties
└── src/test/java/org/company/user/
    ├── application/EmployeeServiceTest.java
    ├── infrastructure/
    │   ├── in/EmployeeControllerTest.java, in/mapper/EmployeeInMapperTest.java
    │   ├── exception/GlobalExceptionHandlerTest.java
    │   └── out/repository/EmployeeRepositoryAdapterTest.java, out/repository/mapper/EmployeeEntityMapperTest.java
    └── factorymodel/ModelFactory.java
```

## Cambios aplicados — 2026-09-17

Se aplicaron **todos** los hallazgos (Crítico a Bajo), confirmados por el usuario:

1. **NPE en login** — `EmployeeJpaRepository.findByEmail` ahora retorna `Optional<EmployeeEntity>`; el puerto `EmployeeRepository.findByEmail` retorna `Optional<Employee>`; `EmployeeService.validateEmployee` lanza `EMPLOYEE_INVALID_CREDENTIALS` si el email no existe, sin llegar a `checkPassword`. Se agregó el test `EmployeeServiceTest.validateEmployeeWhenEmailDoesNotExist` y `EmployeeRepositoryAdapterTest.findEmployeeEntityWhenEmailDoesNotExist`.
2. **Logging del exception handler** — `GlobalExceptionHandler.handleGenericErrors` ahora recibe la excepción y la loguea (`log.error`); se agregó `@Slf4j` también en `EmployeeService` y `EmployeeRepositoryAdapter` (logs guardados con `isXxxEnabled()` para cumplir la regla PMD `GuardLogStatement`). Se agregó `GlobalExceptionHandlerTest`.
3. **Credenciales de BD** — `application.properties` ahora usa `${DB_URL}`, `${DB_USERNAME}`, `${DB_PASSWORD}` con defaults locales razonables (sin password por defecto).
4. **Cobertura de Jacoco** — se redujeron las exclusiones (ya no excluyen mappers ni `GlobalExceptionHandler`); se agregaron `EmployeeInMapperTest` y `EmployeeEntityMapperTest`. `./gradlew test jacocoTestCoverageVerification` pasa con el umbral de 80%.
5. **Flyway** — se agregó la dependencia `org.flywaydb:flyway-mysql` y `src/main/resources/db/migration/V1__create_employees_table.sql` con `email UNIQUE NOT NULL`.
6. **Naming Employer→Employee** — renombrados `EmployerController`→`EmployeeController`, `EmployerRequest`→`EmployeeRequest`, `EmployeeValidateRequest`→`EmployeeCredentialsRequest`, `EmployeeValidation`→`EmployeeCredentials` (dominio), `EmployeJpaRepository`→`EmployeeJpaRepository`, `entityToEmploy`→`entityToEmployee`. Confirmado con el usuario como error de naming, no distinción de dominio intencional.
7. **MapStruct** — `EmployeeInMapper` y `EmployeeEntityMapper` pasaron de clases con mapeo manual a interfaces `@Mapper(componentModel = "spring")`; el mapeo campo a campo es generado en compilación.
8. **Mensaje `VALIDATION_CP`** — corregido en `messages_en.properties` de "10 digits" a "5 digits".
9. **Logging general** — agregado en `EmployeeService` (creación de empleado) y `EmployeeRepositoryAdapter` (intento de email duplicado).
10. **Tipo de retorno del controller** — `EmployeeController.saveEmployee`/`validateEmployee` ahora retornan `ResponseEntity<Void>`.

**Verificación:** `./gradlew compileJava compileTestJava` y `./gradlew test jacocoTestCoverageVerification pmdMain` corren en verde (18 tests, 0 fallos, cobertura ≥80%, `pmdMain` sin violaciones).

**Deuda preexistente no introducida por este review** (confirmada comparando contra el baseline antes de los cambios, vía `git stash`): `pmdTest` y `spotbugsTest` ya fallaban en la rama antes de este review — `pmdTest` por 14 violaciones de estilo en los tests existentes (`UnitTestAssertionsShouldIncludeMessage`, `LocalVariableCouldBeFinal`, etc.) y `spotbugsTest` por `HARD_CODE_PASSWORD` (FindSecBugs) sobre el literal `"Password123"` en `ModelFactory`, usado como fixture de test. No se tocó este punto por estar fuera del alcance del review arquitectónico; queda como hallazgo de higiene de tests para una iteración futura si se desea que `./gradlew check` corra 100% en verde.
