# 📚 Sistema de Biblioteca - Spring Boot

Este proyecto es un **Sistema de Gestión de Biblioteca** desarrollado con **Spring Boot** bajo una **arquitectura multicapa**, que permite administrar usuarios, libros y préstamos.  
El sistema implementa **JPA** con anotaciones, **DTOs** para la transferencia de datos, **servicios independientes** para la lógica de negocio y **controladores REST** para exponer los endpoints de la API.

---

## 🏗️ Arquitectura del Proyecto

El proyecto está dividido en capas que facilitan la mantenibilidad y escalabilidad:

- **controller:** maneja las peticiones HTTP y expone los endpoints (`LibroController`, `PrestamoController`, `UsuarioController`).
- **service:** contiene la lógica de negocio y las validaciones (`LibroService`, `PrestamoService`, `UsuarioService`).
- **repository:** gestiona la persistencia de datos mediante interfaces JPA (`ILibroRepository`, `IUsuarioRepository`, `IPrestamoRepository`).
- **model:** define las entidades del sistema (`Libro`, `Usuario`, `Prestamo`).
- **dto:** agrupa objetos de transferencia de datos (`PrestamoDTO`, `PrestamoInfoDTO`).

---

## ⚙️ Funcionalidades Principales

### 👤 Usuarios
- Crear un usuario (`POST /usuarios/crear`)
- Listar todos los usuarios (`GET /usuarios/traer`)
- Buscar usuario por ID (`GET /usuarios/traer/{id}`)
- Actualizar usuario (`PUT /usuarios/editar/{id}`)
- Eliminar usuario (`DELETE /usuarios/eliminar/{id}`)

### 📖 Libros
- Registrar un nuevo libro (`POST /libros/crear`)
- Listar todos los libros (`GET /libros/traer`)
- Buscar libro por ID (`GET /libros/traer/{id}`)
- Actualizar libro (`PUT /libros/editar/{id}`)
- Eliminar libro (`DELETE /libros/eliminar/{id}`)

### 🔄 Préstamos
- Registrar un préstamo (`POST /prestamos/crear`)
- Listar todos los préstamos (`GET /prestamos/traer`)
- Buscar préstamo por ID (`GET /prestamos/traer/{id}`)
- Actualizar préstamo (`PUT /prestamos/editar/{id}`)
- Eliminar préstamo (`DELETE /prestamos/eliminar/{id}`)

---

## 🔗 Relaciones entre Entidades

- Un **Usuario** puede tener varios **Préstamos**.  
- Un **Libro** solo puede estar en un **préstamo activo** a la vez.  
- Cuando un libro es prestado, su estado cambia a `NO DISPONIBLE`.  
- Al eliminar un préstamo, el libro vuelve a estar `DISPONIBLE`.

---

## 🧰 Tecnologías Utilizadas

| Tecnología | Descripción |
|-------------|-------------|
| ☕ **Java 17+** | Lenguaje principal |
| 🌱 **Spring Boot 3+** | Framework backend |
| 🧩 **Spring Data JPA** | Persistencia con anotaciones ORM |
| 🗃️ **MySQL 8** | Base de datos relacional |
| 🧱 **Maven** | Gestión de dependencias |
| 🧾 **Lombok** | Reducción de código repetitivo |
| 🧪 **Postman** | Pruebas de endpoints |
| 📋 **DTOs** | Transferencia de datos entre capas |

---

🐳 Ejecución con Docker

El proyecto incluye configuración para ejecutarse fácilmente dentro de contenedores Docker, evitando la instalación manual de MySQL o configuraciones locales complejas.
🔹 Requisitos previos
    Tener instalado Docker
🔹 Configuración de credenciales

El archivo docker-compose.yml utiliza variables de entorno definidas en un archivo .env (no se sube al repositorio por seguridad).
Antes de ejecutar el proyecto, asegúrate de crear un archivo .env junto al docker-compose.yml con tus propias credenciales:

# Archivo .env (ejemplo)
MYSQL_ROOT_PASSWORD=tu_root_password
MYSQL_USER=appuser
MYSQL_PASSWORD=tu_password_segura

DB_USER=appuser
DB_PASSWORD=tu_password_segura
DB_URL=jdbc:mysql://bibliot:3306/biblioteca?createDatabaseIfNotExist=true&serverTimezone=UTC&allowPublicKeyRetrieval=true

⚠️ Importante:
Las credenciales (MYSQL_USER, MYSQL_PASSWORD, DB_USER, DB_PASSWORD) deben coincidir.
No uses el usuario root para la conexión del backend.

🔹 Levantar el entorno
En la carpeta donde se encuentra el docker-compose.yml, ejecuta:
    docker-compose up -d

Esto iniciará dos contenedores:
bibliot: base de datos MySQL 8.
biblioteca: aplicación Spring Boot.

Accede al backend desde:
👉 http://localhost:8080

🔹 Detener los contenedores
Cuando termines de trabajar, puedes detener todo con:
docker-compose down

Los datos se mantienen gracias al volumen mysql_data, por lo que no se pierde la información al apagar el contenedor.

---

## ⚙️ Configuración del Proyecto

Archivo `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/TU_BD?useSSL=false&serverTimezone=UTC
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASENIA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.database-platform=org.hibernate.dialect.MySQL8Dialect
server.port=8080


## 🧪 Colección de Postman

El proyecto incluye una colección lista para probar todos los endpoints de la API.  
Esta se encuentra en el archivo:

Biblioteca.postman_collection.json

La colección contiene las peticiones para los módulos de **Usuarios**, **Libros** y **Préstamos**, organizadas y listas para ejecutar en **Postman**.

### ▶️ Cómo importarla en Postman

1. Abre **Postman**.  
2. Haz clic en **Import** (parte superior izquierda).  
3. Selecciona la opción **File**.  
4. Carga el archivo: Biblioteca.postman_collection.json
5. Una vez importada, podrás probar directamente todos los endpoints del sistema desde la interfaz de Postman.


src/
├── main/
│   ├── java/com/proyecto/biblioteca/
│   │   ├── controller/
│   │   │   ├── LibroController.java
│   │   │   ├── PrestamoController.java
│   │   │   └── UsuarioController.java
│   │   ├── dto/
│   │   │   ├── PrestamoDTO.java
│   │   │   └── PrestamoInfoDTO.java
│   │   ├── model/
│   │   │   ├── Libro.java
│   │   │   ├── Prestamo.java
│   │   │   └── Usuario.java
│   │   ├── repository/
│   │   │   ├── ILibroRepository.java
│   │   │   ├── IPrestamoRepository.java
│   │   │   └── IUsuarioRepository.java
│   │   ├── service/
│   │   │   ├── ILibroService.java
│   │   │   ├── IPrestamoService.java
│   │   │   ├── IUsuarioService.java
│   │   │   ├── LibroService.java
│   │   │   ├── PrestamoService.java
│   │   │   └── UsuarioService.java
│   │   └── BibliotecaApplication.java
│   └── resources/
│       ├── application.properties
│       └── Biblioteca.postman_collection.json
└── test/
    └── java/com/proyecto/biblioteca/
        └── BibliotecaApplicationTests.java

🧑‍💻 Autor
Jonatan Lombo
Desarrollador Backend Java
Proyecto académico - Arquitectura multicapa con Spring Boot y JPA        
