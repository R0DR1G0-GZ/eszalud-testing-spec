# Decisiones de diseño y de pruebas del modelo (EsZalud)

**Alumno:** Giron Zarate Rodrigo
**Rama:** tdd/GironZarate_Rodrigo

## 1. Cómo trabajé

Desarrollé la capa Model de EsZalud aplicando TDD. Para cada caso obligatorio de la EFP seguí el ciclo completo: primero escribí el test y comprobé que fallara por la razón correcta (RED), hice un commit `test(...)`, luego escribí el código mínimo para que pasara (GREEN) y hice un commit `feat(...)`. Cuando vi código repetido, hice un REFACTOR y corrí toda la suite antes de cada commit `refactor(...)`.

Los mensajes de commit están en inglés con el formato `type(scope): description`, los `@DisplayName` de los tests están en español y los nombres de los métodos siguen el formato `shouldXxx`. Las clases de producción están en `src/main/java/pe/saludpublica/model` y los tests en `src/test/java/pe/saludpublica/model`.

Entregué 18 tests en total: Paciente 4, Doctor 5, Especialidad 2 y CitaMedica 7.

Antes de empezar los ciclos dejé registrada la configuración del proyecto en commits `build(...)`: el `.gitignore`, Gradle con JUnit 6 y Mockito, y el wrapper de Gradle.

## 2. Decisiones generales

- **Qué valido:** en los campos obligatorios rechazo el valor `null` lanzando `IllegalArgumentException` con un mensaje en español. No validé cadenas vacías, espacios en blanco ni formatos (por ejemplo, de teléfono o correo), porque la EFP no lo pide y no quise inventar reglas.
- **Campos opcionales:** no los validé. Son `telefono` y `correo` (Paciente), `especialidad` (Doctor), `descripcion` (Especialidad) y `motivo` (CitaMedica).
- **Inmutabilidad:** todos los atributos son `private final`. Cada clase tiene solo constructor y getters, sin setters, para que un objeto válido no pueda quedar inválido después.
- **Casos de verificación de atributos:** PAC-005, DOC-006, ESP-003 y CIT-008 piden comprobar que los getters devuelvan lo recibido. Ya lo comprueban los tests de creación válida (PAC-001, DOC-001, ESP-001 y CIT-001), que verifican todos los getters. Por eso no les agregué tests duplicados.
- **Por qué no usé Mockito ni MongoDB:** la capa Model que desarrollé no depende de la base de datos ni de otras clases, porque las referencias entre modelos son Strings. Por eso no había nada que simular con Mockito y mis tests no necesitan MongoDB ni JavaFX. Dejé Mockito configurado en build.gradle por si se necesitara más adelante.

## 3. Decisiones por modelo

### Paciente
- Campos: documento, nombres, apellidos, telefono y correo (todos `String`).
- Obligatorios: documento, nombres y apellidos. Mensajes: "El documento es obligatorio", "Los nombres son obligatorios" y "Los apellidos son obligatorios".
- Casos con ciclo completo: PAC-001 a PAC-004.

### Doctor
- Campos: documento, nombres, apellidos, colegiatura y especialidad (todos `String`).
- Guardé la especialidad del doctor como `String` (un identificador o referencia) y no como objeto `Especialidad`, para que los modelos no dependan entre sí y las pruebas sigan siendo unitarias.
- Obligatorios: documento, nombres, apellidos y colegiatura. La especialidad no se valida en el constructor.
- Casos con ciclo completo: DOC-001 a DOC-005.

### Especialidad
- Campos: nombre y descripcion (`String`).
- Obligatorio: nombre ("El nombre es obligatorio"). La descripción es opcional.
- Casos con ciclo completo: ESP-001 y ESP-002.

### CitaMedica
- Campos: paciente, doctor y especialidad (`String`), fechaHora (`LocalDateTime`), motivo (`String`) y estado (`EstadoCita`).
- **Tipo de los identificadores:** paciente, doctor y especialidad son `String` (identificador o referencia), y no objetos `Paciente`, `Doctor` o `Especialidad`, por el mismo motivo que en Doctor: evitar dependencias entre modelos.
- **Tipo de la fecha:** usé `LocalDateTime` porque la cita necesita fecha y hora y no requiere zona horaria.
- **Estado:** creé el enum `EstadoCita` con los valores PROGRAMADA, ATENDIDA y CANCELADA.
- Obligatorios: paciente, doctor, especialidad, fechaHora y estado. El motivo es opcional.
- **CIT-006 (estado `null`):** la EFP no dice qué hacer, así que tuve que decidir. Elegí **rechazarlo** con `IllegalArgumentException` ("El estado es obligatorio"). Asignar PROGRAMADA automáticamente habría sido inventar una regla de negocio que nadie pidió, y además podría ocultar un error de quien crea la cita.
- **CIT-007 (estado válido se conserva):** este test pasó directamente, porque el código ya lo cumplía después de CIT-006. Por eso tiene un commit `test` y no tiene commit `feat`.
- Casos: CIT-001 a CIT-007 (CIT-008 queda cubierto por CIT-001).

## 4. Refactorizaciones

- **Paciente, Doctor y CitaMedica:** extraje un método privado `requerido(...)` para eliminar los `if (campo == null)` repetidos. En CitaMedica lo hice genérico para poder validar también `LocalDateTime` y `EstadoCita`. Después de cada refactor corrí toda la suite y siguió en verde.
- **Especialidad:** no la refactoricé porque solo tiene una validación y no hay código repetido que eliminar.

## 5. Observaciones sobre mi proceso

- En el primer ciclo de cada modelo, el commit RED incluyó la clase como esqueleto mínimo (por ejemplo, getters que devuelven `null`) para que el test compilara y fallara por la aserción y no por un error de compilación. La implementación real quedó en el commit GREEN.
- En la clase `CitaMedica`, el commit RED de CIT-001 también incluyó el enum `EstadoCita`, porque el constructor lo necesita para compilar.