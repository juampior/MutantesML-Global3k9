# 🧬 Detector de Mutantes - Magneto Recruitment System

Sistema de detección de mutantes basado en análisis de secuencias de ADN, desarrollado para el reclutamiento de Magneto en su lucha contra los X-Men.

## 📋 Descripción

Este proyecto implementa un algoritmo eficiente para detectar si un humano es mutante analizando su secuencia de ADN. Un humano es considerado mutante si se encuentran **más de una secuencia de cuatro letras iguales** (A, T, C, G) en cualquier dirección: horizontal, vertical u oblicua (diagonal).

### Ejemplo de ADN Mutante

```
A T G C G A
C A G T G C
T T A T G T    ← Secuencia horizontal de 4 A's
A G A A G G    
C C C C T A    ← Secuencia horizontal de 4 C's
T C A C T G
```
## 🌐 Deploy en Render

El proyecto está desplegado en Render (cloud computing gratuito):

**URL de producción:** `https://mutantesml-global3k9.onrender.com`

### Endpoints en producción:
- POST `https://mutantesml-global3k9.onrender.com/mutant`
- GET `https://mutantesml-global3k9.onrender.com/stats`

## 🚀 Características

- ✅ **Nivel 1**: Algoritmo optimizado de detección de mutantes
- ✅ **Nivel 2**: API REST con endpoints para verificación de ADN
- ✅ **Nivel 3**: Persistencia en base de datos H2 con estadísticas
- ✅ **Tests automatizados** con cobertura > 80%
- ✅ **Deployed en Render** (cloud computing)
- ✅ **Arquitectura en capas** (Controller → Service → Repository)

## 🛠️ Tecnologías Utilizadas

- Java 17+
- lombok
- Spring Boot 3.x
- Spring Data JPA
- H2 Database (en memoria)
- Maven (gestión de dependencias)
- JUnit 5 (testing)
- Mockito (mocking en tests)

## 📦 Instalación

### Prerrequisitos

- Java JDK 17 o superior
- Maven 3.6+
- Git

### Clonar el repositorio

```bash
git clone https://github.com/juampior/MutantesML-Global3k9.git
cd MutantesML-Global3k9
```

### Compilar el proyecto

```bash
mvn clean install
```

### Ejecutar la aplicación

```bash
mvn spring-boot:run
```

La aplicación se ejecutará en `http://localhost:8080`

## 🔌 API Endpoints

### 1. Verificar si un ADN es mutante

**POST** `/mutant/`

**Request Body:**
```json
{
  "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
}
```

**Respuestas:**
- `200 OK` - El ADN es mutante
- `403 Forbidden` - El ADN es humano (no mutante)

**Ejemplo con cURL:**
```bash
curl -X POST http://localhost:8080/mutant/ \
  -H "Content-Type: application/json" \
  -d '{
    "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
  }'
```

### 2. Obtener estadísticas

**GET** `/stats`

**Response:**
```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

**Ejemplo con cURL:**
```bash
curl http://localhost:8080/stats
```

## 🏗️ Arquitectura del Proyecto

```
MutantesML-Global3k9/
├── .gradle/                                         # Archivos de Gradle
├── .idea/                                           # Configuración de IntelliJ
├── build/                                           # Archivos compilados
├── gradle/                                          # Wrapper de Gradle
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.example.MutantesML_Global3k9/
│   │   │       ├── config/
│   │   │       │   └── SwaggerConfig.java          # Configuración Swagger/OpenAPI
│   │   │       ├── controller/
│   │   │       │   └── MutantController.java       # Endpoints REST
│   │   │       ├── dto/
│   │   │       │   ├── DnaRequest.java             # DTO para peticiones
│   │   │       │   ├── ErrorResponse.java          # DTO para errores
│   │   │       │   └── StatsResponse.java          # DTO para estadísticas
│   │   │       ├── entity/
│   │   │       │   └── DnaRecord.java              # Entidad JPA
│   │   │       ├── exception/
│   │   │       │   ├── DnaHashCalculationException.java
│   │   │       │   └── GlobalExceptionHandler.java # Manejo global de errores
│   │   │       ├── repository/
│   │   │       │   └── DnaRecordRepository.java    # Acceso a datos con JPA
│   │   │       ├── service/
│   │   │       │   ├── MutantDetector.java         # Algoritmo de detección
│   │   │       │   ├── MutantService.java          # Lógica de negocio
│   │   │       │   └── StatsService.java           # Servicio de estadísticas
│   │   │       ├── validation/
│   │   │       │   └── MutantesMLGlobal3k9Application.java
│   │   │       └── MutantesMLGlobal3k9Application.java  # Clase principal Spring Boot
│   │   └── resources/
│   │       └── application.properties               # Configuración de la app
│   └── test/
│       └── java/
│           └── com.example.MutantesML_Global3k9/
│               ├── config/
│               │   └── SwaggerConfigTest.java
│               ├── controller/
│               │   └── MutantControllerTest.java    # Tests de endpoints
│               ├── dto/
│               │   ├── DnaRequestTest.java
│               │   ├── ErrorResponseTest.java
│               │   └── StatsResponseTest.java
│               ├── exception/
│               │   ├── DnaHashCalculationExceptionTest.java
│               │   └── GlobalExceptionHandlerTest.java
│               └── service/
│                   ├── MutantDetectorTest.java      # Tests del algoritmo
│                   ├── MutantServiceTest.java
│                   └── StatsServiceTest.java
├── .gitattributes                                   # Atributos de Git
├── .gitignore                                       # Archivos ignorados por Git
├── build.gradle                                     # Configuración de Gradle
├── gradlew                                          # Script Gradle para Unix/Mac
├── gradlew.bat                                      # Script Gradle para Windows
├── settings.gradle                                  # Configuración del proyecto Gradle
├── Dockerfile                                       # Configuración Docker
├── DiagramaSecuencia-GET-stats.puml                # Diagrama de secuencia GET
├── DiagramaSecuencia-POST-mutant.puml              # Diagrama de secuencia POST
├── HELP.md                                          # Ayuda de Spring Boot
├── Links-Deploy-Repositorio.txt                    # Enlaces de deploy
└── README.md                                        # Este archivo
```

### Capas del Sistema

1. **Config Layer**: Configuración de Swagger/OpenAPI para documentación de la API
2. **Controller Layer**: Expone los endpoints REST y maneja las peticiones HTTP
3. **DTO Layer**: Objetos de transferencia de datos (Request/Response)
4. **Service Layer**: Contiene la lógica de negocio y el algoritmo de detección
5. **Repository Layer**: Gestiona la persistencia de datos con Spring Data JPA
6. **Entity Layer**: Define el modelo de datos (DnaRecord)
7. **Exception Layer**: Manejo centralizado de excepciones y errores
8. **Validation Layer**: Validaciones personalizadas (si aplica)

### Componentes Adicionales

- **Swagger UI**: Documentación interactiva de la API disponible en `/swagger-ui.html`
- **Diagramas UML**: Incluye diagramas de secuencia para los endpoints principales
- **Dockerfile**: Configuración para containerización con Docker
- **Tests completos**: Cobertura de tests para todas las capas del sistema

## 🧪 Testing

### Ejecutar todos los tests

```bash
mvn test
```

### Ver reporte de cobertura

```bash
mvn jacoco:report
```

El reporte se generará en `target/site/jacoco/index.html`

### Cobertura Actual

- ✅ Cobertura de líneas: > 80%
- ✅ Tests unitarios para `MutantDetector`
- ✅ Tests de integración para endpoints
- ✅ Tests para servicios y repositorios

## 🔧 Configuración

### Base de Datos H2

La configuración de H2 se encuentra en `application.properties`:

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
spring.jpa.hibernate.ddl-auto=update
```

**Acceder a la consola H2:**
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: (dejar vacío)


## 📊 Algoritmo de Detección

El algoritmo implementa las siguientes optimizaciones:

1. **Validación temprana**: Verifica el formato del ADN antes de procesar
2. **Terminación anticipada**: Se detiene al encontrar más de una secuencia
3. **Búsqueda eficiente**: Recorre la matriz una sola vez en todas las direcciones
4. **Complejidad temporal**: O(N²) en el peor caso, donde N es el tamaño de la matriz

### Direcciones de búsqueda:
- ➡️ Horizontal (izquierda a derecha)
- ⬇️ Vertical (arriba a abajo)
- ↘️ Diagonal descendente
- ↗️ Diagonal ascendente




## 👤 Autor: Ortega Rivero, Oscar Juan Pablo


- GitHub: [@juampior](https://github.com/juampior)



⭐ Si este proyecto te fue útil, considera darle una estrella en GitHub!

**Desarrollado con ❤️ para el reclutamiento de Magneto** 🧲


